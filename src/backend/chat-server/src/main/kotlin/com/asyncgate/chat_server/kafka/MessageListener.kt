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
    fun directMessageListener(directChat: DirectMessage) {
        val msg = HashMap<String, String>()
        msg["type"] = "message"
        msg["userId"] = java.lang.String.valueOf(directChat.userId)
        msg["name"] = directChat.name
        msg["profileImage"] = directChat.profileImage
        msg["message"] = directChat.content
        msg["time"] = java.lang.String.valueOf(directChat.localDateTime)
        msg["id"] = directChat.id ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)

        val serializable = objectMapper.writeValueAsString(msg)
        template.convertAndSend("/topic/direct/" + directChat.channelId, serializable)
    }
}
