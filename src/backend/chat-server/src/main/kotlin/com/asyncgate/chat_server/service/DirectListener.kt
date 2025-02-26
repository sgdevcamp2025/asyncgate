package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Component

@Component
class DirectListener(
    private val template: SimpMessagingTemplate,
    private val objectMapper: ObjectMapper,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(DirectListener::class.java)
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topic.direct-message}"],
        groupId = "\${spring.kafka.consumer.group-id.direct-message}",
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
    // ToDo KafkaListener, group-id 에서 업로드 이벤트 설정 추가,
}
