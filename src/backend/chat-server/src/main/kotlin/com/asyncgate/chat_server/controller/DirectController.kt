package com.asyncgate.chat_server.controller

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
        val directMessage = directMessageCreate.toDomain(userId)
        directService.send(directMessage)
    }

    @MessageMapping("/read")
    fun read(@Payload readStatus: ReadStatus) {
        directService.updateReadStatus(readStatus)
    }

    @MessageMapping("/typing")
    fun typing(@Payload directTyping: DirectMessageTyping, message: Message<*>) {
        val jwtToken = StompSecurityContext.extractJwtToken(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directTyping.toDomain(userId)
        directService.typing(directMessage)
    }

    @MessageMapping("/edit")
    fun edit(@Payload directEdit: DirectMessageEdit, message: Message<*>) {
        val jwtToken = StompSecurityContext.extractJwtToken(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directEdit.toDomain(userId)
        directService.edit(directMessage)
    }

    @MessageMapping("/delete")
    fun sendDeleteMessage(@Payload directDelete: DirectMessageDelete, message: Message<*>) {
        val jwtToken = StompSecurityContext.extractJwtToken(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directDelete.toDomain(userId)
        directService.delete(directMessage)
    }
}
