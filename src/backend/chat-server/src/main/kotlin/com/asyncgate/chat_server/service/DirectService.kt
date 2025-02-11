package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.asyncgate.chat_server.kafka.KafkaProperties
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.repository.ReadStatusRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface DirectService {
    fun send(directMessage: DirectMessage)
    fun updateReadStatus(readStatus: ReadStatus)
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

    @Transactional
    override fun send(directMessage: DirectMessage) {
        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(kafkaProperties.topic.directMessage, key, directMessage)

        // ToDo 추후 저장 서버 분리
        directMessageRepository.save(directMessage)
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topic.direct-message}"],
        groupId = "\${spring.kafka.consumer.group-id.direct}",
        containerFactory = "directFactory"
    )
    fun directMessageListener(directMessage: DirectMessage) {
        val msg = HashMap<String, String>()
        msg["type"] = "message"
        msg["userId"] = java.lang.String.valueOf(directMessage.userId)
        msg["name"] = directMessage.name
        msg["profileImage"] = directMessage.profileImage
        msg["message"] = directMessage.content
        msg["time"] = java.lang.String.valueOf(directMessage.createdAt)
        msg["id"] = directMessage.id ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/direct-message/" + directMessage.channelId, serializable)
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
    fun readStatusListener(readStatus: ReadStatus) {
        // ToDo 캐싱 도입
        
        val msg = mapOf(
            "type" to "read-status",
            "userId" to readStatus.userId,
            "channelId" to readStatus.channelId,
            "lastReadMessageId" to readStatus.lastReadMessageId
        )

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/read-status/" + readStatus.channelId, serializable)
    }
}
