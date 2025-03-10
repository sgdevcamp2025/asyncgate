package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.client.GuildClient
import com.asyncgate.chat_server.controller.DirectPagingResponse
import com.asyncgate.chat_server.controller.FileRequest
import com.asyncgate.chat_server.controller.FileUploadResponse
import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType
import com.asyncgate.chat_server.domain.LoginSession
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.domain.Type
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.asyncgate.chat_server.kafka.KafkaProperties
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.repository.ReadStatusRepository
import com.asyncgate.chat_server.support.utility.S3Util
import com.asyncgate.chat_server.support.utility.toDomain
import com.asyncgate.chat_server.support.utility.toEntity
import com.asyncgate.chat_server.support.utility.toFileResponse
import com.asyncgate.chat_server.support.utility.toPagingResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface DirectService {
    fun send(directMessage: DirectMessage, sessionId: String)
    fun updateReadStatus(readStatus: ReadStatus)
    fun typing(directMessage: DirectMessage)
    fun edit(directMessage: DirectMessage)
    fun delete(directMessage: DirectMessage)
    fun upload(fileRequest: FileRequest, userId: String): FileUploadResponse
    fun readPaging(channelId: String, page: Int, size: Int): DirectPagingResponse
}

@Service
class DirectServiceImpl(
    private val kafkaTemplateForDirectMessage: KafkaTemplate<String, DirectMessage>,
    private val kafkaTemplateForReadStatus: KafkaTemplate<String, ReadStatus>,
    private val kafkaTemplateForFileUpload: KafkaTemplate<String, FileUploadResponse>,
    private val kafkaProperties: KafkaProperties,

    private val readStatusRepository: ReadStatusRepository,
    private val directMessageRepository: DirectMessageRepository,

    private val s3Util: S3Util,
    private val stateSessionService: StateSessionService,
    private val guildClient: GuildClient,
) : DirectService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(DirectServiceImpl::class.java)
    }

    @Transactional
    override fun send(directMessage: DirectMessage, sessionId: String) {
        log.info("📌 DirectServiceImpl.send")
        val key = directMessage.channelId

        // ToDo 추후 저장 서버 분리
        val saveDirectMessage = directMessageRepository.save(directMessage)

        // Direct는 길드 > 카테고리 > 채널 과 같은 개념이 아닌 단순히 채널 ID가 directId
        val directId = directMessage.channelId
        val memberIds = guildClient.getDirectDetail(directId)

        val loginSession = LoginSession(
            type = Type.DIRECT,
            sessionId = sessionId,
            userId = directMessage.userId,
            communityId = directMessage.channelId,
            ids = memberIds
        )

        stateSessionService.sendLoginSessionToStateServer(loginSession)
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directMessage, key, saveDirectMessage)
    }

    @Transactional
    override fun updateReadStatus(readStatus: ReadStatus) {
        val key = readStatus.channelId

        val existingReadStatus = readStatusRepository.findByUserIdAndChannelId(
            userId = readStatus.userId,
            channelId = readStatus.channelId
        )

        if (existingReadStatus != null) {
            val lastReadMessageId = checkNotNull(existingReadStatus.lastReadMessageId) {
                "Logic error: lastReadMessageId should never be null for an existing read status"
            }
            existingReadStatus.updateLastReadMessageId(lastReadMessageId)
            readStatusRepository.save(existingReadStatus)
            kafkaTemplateForReadStatus.send(kafkaProperties.topic.readStatus, key, existingReadStatus)
            return
        }

        val newReadStatus = ReadStatus.create(
            userId = readStatus.userId,
            channelId = readStatus.channelId
        )
        readStatusRepository.save(newReadStatus)
        kafkaTemplateForReadStatus.send(kafkaProperties.topic.readStatus, key, newReadStatus)
    }

    override fun typing(directMessage: DirectMessage) {
        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directAction, key, directMessage)
    }

    @Transactional
    override fun edit(directMessage: DirectMessage) {
        checkNotNull(directMessage.id) { "Logic error: 수정시에는 id가 필수이므로 존재하지 않을 수 없음" }
        val pastMessage = directMessageRepository.findById(directMessage.id)
            ?: throw IllegalStateException("Logic error: 이미 Null Check 완료")

        validPermission(directMessage, pastMessage)

        // 도메인 멤버 메서드를 활용하여 수정 결과를 생성
        val deletedMessage = pastMessage.markDeleted()

        checkNotNull(directMessage.name) { "Logic error: 수정시 name이 필수로 들어옴" }
        checkNotNull(directMessage.content) { "Logic error: 수정시 content가 필수로 들어옴" }

        val editedMessage = pastMessage.withEdit(directMessage.name, directMessage.content)

        // 기존 메시지를 삭제 상태로 업데이트 (엔티티 변환 후, isDeleted를 true로 설정)
        directMessageRepository.save(deletedMessage.toEntity().copy(isDeleted = true).toDomain())
        // 새로 생성된 수정본 저장
        directMessageRepository.save(editedMessage)

        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directAction, key, directMessage)
    }

    private fun validPermission(
        directMessage: DirectMessage,
        findMessage: DirectMessage,
    ) {
        if (directMessage.userId != findMessage.userId) {
            throw ChatServerException(FailType.DIRECT_MESSAGE_FORBIDDEN)
        }
    }

    @Transactional
    override fun delete(directMessage: DirectMessage) {
        checkNotNull(directMessage.id) {
            "Logic error: 삭제시에는 id가 필수이므로 존재하지 않을 수 없음"
        }

        val findMessage = directMessageRepository.findById(directMessage.id)

        checkNotNull(findMessage) {
            "Logic error: 삭제시에는 전달 받음"
        }

        validPermission(directMessage, findMessage)
        directMessageRepository.delete(findMessage)

        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directAction, key, directMessage)
    }

    @Transactional
    override fun upload(fileRequest: FileRequest, userId: String): FileUploadResponse {
        val directMessage = fileRequest.toDomain(userId, fileRequest.fileType)
        val key = fileRequest.channelId

        when (fileRequest.fileType) {
            DirectMessageType.CODE, DirectMessageType.SNIPPET,
            -> {
                val codeDirectMessage = uploadCode(fileRequest, directMessage)
                val domain = directMessageRepository.save(codeDirectMessage)
                val response = domain.toFileResponse(domain, userId, fileRequest)
                kafkaTemplateForFileUpload.send(kafkaProperties.topic.directUpload, key, response)
                return response
            }

            DirectMessageType.IMAGE, DirectMessageType.VIDEO, DirectMessageType.AUDIO,
            DirectMessageType.DOCUMENT, DirectMessageType.ARCHIVE, DirectMessageType.GIF,
            DirectMessageType.STICKER, DirectMessageType.EMOJI,
            -> {
                val fileDirectMessage = uploadMultipartFile(fileRequest, directMessage)
                val domain = directMessageRepository.save(fileDirectMessage)
                val response = domain.toFileResponse(domain, userId, fileRequest)
                kafkaTemplateForFileUpload.send(kafkaProperties.topic.directUpload, key, response)
                return response
            }

            else -> throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)
        }
    }

    @Transactional(readOnly = true)
    override fun readPaging(channelId: String, page: Int, size: Int): DirectPagingResponse {
        return directMessageRepository.findByChannelId(channelId, page, size).toPagingResponse()
    }

    private fun uploadMultipartFile(
        fileRequest: FileRequest,
        directMessage: DirectMessage,
    ): DirectMessage {
        if (fileRequest.image == null || fileRequest.thumbnail == null) {
            throw ChatServerException(FailType.DIRECT_MESSAGE_BAD_REQUEST)
        }

        val uploadedFileUrl = s3Util.uploadFile(fileRequest.image, DirectMessage::class.java.name)
        val uploadedThumbnailUrl = s3Util.uploadFile(fileRequest.thumbnail, DirectMessage::class.java.name)

        return directMessage.toEntity().copy(
            content = uploadedFileUrl,
            thumbnail = uploadedThumbnailUrl
        ).toDomain()
    }

    private fun uploadCode(
        fileRequest: FileRequest,
        directMessage: DirectMessage,
    ): DirectMessage {
        if (fileRequest.content == null) {
            throw ChatServerException(FailType.DIRECT_MESSAGE_CONTENT_NULL)
        }

        return directMessage.toEntity()
            .copy(
                content = fileRequest.content
            ).toDomain()
    }
}
