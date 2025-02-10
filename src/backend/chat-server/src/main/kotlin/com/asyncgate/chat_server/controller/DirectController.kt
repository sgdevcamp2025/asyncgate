package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessageCreate
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.filter.JwtTokenProvider
import com.asyncgate.chat_server.service.DirectService
import com.asyncgate.chat_server.support.utility.StompSecurityContext
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat/direct")
class DirectController(
    private val directService: DirectService,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @MessageMapping("/send-message")
    fun direct(@Payload directMessageCreate: DirectMessageCreate, message: Message<*>) {
        val jwtToken = StompSecurityContext.extractJwtToken(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directMessageCreate.toDirectMessage(userId)
        directService.send(directMessage, jwtToken)
    }

    @MessageMapping("/read-message")
    fun readDirectMessage(@Payload readStatus: ReadStatus) {
        directService.updateReadStatus(readStatus)
    }
}
