package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.kafka.MessageSender
import org.springframework.beans.factory.annotation.Value
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat/direct")
class DirectController(
    private val messageSender: MessageSender,

    @Value("\${spring.kafka.consumer.topic.direct-chat}")
    private val directChatTopic: String,
) {

    @MessageMapping("/send-message")
    fun direct(@Payload directMessage: DirectMessage) {
        messageSender.send(directChatTopic, directMessage)
    }
}
