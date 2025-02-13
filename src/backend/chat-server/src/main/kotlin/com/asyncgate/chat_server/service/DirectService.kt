package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.asyncgate.chat_server.kafka.KafkaProperties
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.repository.ReadStatusRepository
import com.asyncgate.chat_server.support.utility.IdGenerator
import com.asyncgate.chat_server.support.utility.toDomain
import com.asyncgate.chat_server.support.utility.toEntity
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface DirectService {
    fun send(directMessage: DirectMessage)
    fun updateReadStatus(readStatus: ReadStatus)
    fun typing(directMessage: DirectMessage)
    fun edit(directMessage: DirectMessage)
    fun delete(directMessage: DirectMessage)
}

@Service
class DirectServiceImpl(
    private val kafkaTemplateForDirectMessage: KafkaTemplate<String, DirectMessage>,
    private val kafkaTemplateForReadStatus: KafkaTemplate<String, ReadStatus>,
    private val kafkaProperties: KafkaProperties,

    private val readStatusRepository: ReadStatusRepository,
    private val directMessageRepository: DirectMessageRepository,

    private val objectMapper: ObjectMapper,
    private val template: SimpMessagingTemplate,
) : DirectService {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(DirectServiceImpl::class.java)
    }

    @Transactional
    override fun send(directMessage: DirectMessage) {
        log.info("📌 DirectServiceImpl.send")
        val key = directMessage.channelId

        // ToDo 추후 저장 서버 분리
        val saveDirectMessage = directMessageRepository.save(directMessage)

        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directMessage, key, saveDirectMessage)
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topic.direct-message}"],
        groupId = "\${spring.kafka.consumer.group-id.direct}",
        containerFactory = "directFactory"
    )
    fun dmCreateListener(directMessage: DirectMessage, ack: Acknowledgment) {
        log.info("directMessage = $directMessage")

        val msg = HashMap<String, String>()
        msg["type"] = DirectMessageType.CREATE.toString().lowercase()
        msg["userId"] = java.lang.String.valueOf(directMessage.userId)
        msg["name"] = directMessage.name ?: ""
        msg["profileImage"] = directMessage.profileImage ?: ""
        msg["message"] = directMessage.content ?: ""
        msg["time"] = java.lang.String.valueOf(directMessage.createdAt)
        msg["id"] = directMessage.id ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/direct-message/" + directMessage.channelId, serializable)

        try {
            ack.acknowledge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
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

    @KafkaListener(
        topics = ["\${spring.kafka.topic.read-status}"],
        groupId = "\${spring.kafka.consumer.group-id.read-status}",
        containerFactory = "readStatusFactory"
    )
    fun dmReadListener(readStatus: ReadStatus, ack: Acknowledgment) {
        // ToDo 캐싱 도입

        val msg = mapOf(
            "type" to "read-status",
            "userId" to readStatus.userId,
            "channelId" to readStatus.channelId,
            "lastReadMessageId" to readStatus.lastReadMessageId
        )

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/read-status/" + readStatus.channelId, serializable)

        try {
            ack.acknowledge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun typing(directMessage: DirectMessage) {
        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directAction, key, directMessage)
    }

    @Transactional
    override fun edit(directMessage: DirectMessage) {
        checkNotNull(directMessage.id) {
            "Logic error: 수정시에는 id가 필수이므로 존재하지 않을 수 없음"
        }

        val pastMessage = directMessageRepository.findById(directMessage.id)

        checkNotNull(pastMessage) {
            "Logic error: 이미 Null Check 완료"
        }

        validPermission(directMessage, pastMessage)

        val deletedEntity = pastMessage.toEntity().copy(isDeleted = true)
        directMessageRepository.save(deletedEntity.toDomain())

        val updatedMessage = pastMessage.toEntity().copy(
            id = IdGenerator.generate(),
            name = directMessage.name,
            content = directMessage.content,
            type = DirectMessageType.EDIT
        )
        directMessageRepository.save(updatedMessage.toDomain())

        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directAction, key, directMessage)
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

    private fun validPermission(
        directMessage: DirectMessage,
        findMessage: DirectMessage,
    ) {
        if (directMessage.userId != findMessage.userId) {
            throw ChatServerException(FailType.DIRECT_MESSAGE_FORBIDDEN)
        }
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topic.direct-action}"],
        groupId = "\${spring.kafka.consumer.group-id.direct-action}",
        containerFactory = "directActionFactory"
    )
    fun dmActionListener(directMessage: DirectMessage, ack: Acknowledgment) {
        val msg = HashMap<String, String>()

        when (directMessage.type) {
            DirectMessageType.EDIT -> {
                msg["type"] = DirectMessageType.EDIT.toString()
                msg["userId"] = directMessage.userId
                msg["channelId"] = directMessage.channelId
                msg["content"] = directMessage.content ?: ""
            }
            DirectMessageType.DELETE -> {
                msg["id"] = directMessage.id ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)
                msg["type"] = DirectMessageType.DELETE.toString()
                msg["userId"] = directMessage.userId
                msg["channelId"] = directMessage.channelId
            }
            DirectMessageType.TYPING -> {
                msg["type"] = DirectMessageType.TYPING.toString()
                msg["userId"] = directMessage.userId
                msg["channelId"] = directMessage.channelId
            }
            else -> return
        }

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/direct-message/" + directMessage.channelId, serializable)

        try {
            ack.acknowledge()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
