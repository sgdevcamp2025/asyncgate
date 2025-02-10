package com.asyncgate.chat_server.kafka

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service

@Service
class MessageListener(
    private val objectMapper: ObjectMapper,
    private val template: SimpMessagingTemplate,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.consumer.topic.direct-chat}"],
        groupId = "direct-group",
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
        template.convertAndSend("/topic/direct/" + directMessage.channelId, serializable)
    }
}
