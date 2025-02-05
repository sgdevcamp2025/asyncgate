package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.service.DirectService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat/direct")
class DirectController(
    private val directService: DirectService,
) {

    @MessageMapping("/send-message")
    fun direct(@Payload directMessage: DirectMessage) {
        directService.send(directMessage)
    }

    @MessageMapping("/read-message")
    fun readDirectMessage(@Payload readStatus: ReadStatus) {
        directService.updateReadStatus(readStatus)
    }
}
