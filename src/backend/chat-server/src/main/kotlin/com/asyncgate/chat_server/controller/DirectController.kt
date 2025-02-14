package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.filter.JwtTokenProvider
import com.asyncgate.chat_server.service.DirectService
import com.asyncgate.chat_server.support.response.SuccessResponse
import com.asyncgate.chat_server.support.utility.CustomSecurityContext
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class DirectController(
    private val directService: DirectService,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @MessageMapping("/chat/direct/send-message")
    fun direct(@Payload directMessageCreate: DirectMessageCreate, message: Message<*>) {
        val jwtToken = CustomSecurityContext.extractJwtTokenForStomp(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directMessageCreate.toDomain(userId)
        directService.send(directMessage)
    }

    @MessageMapping("/chat/direct/read")
    fun read(@Payload readStatus: ReadStatus) {
        directService.updateReadStatus(readStatus)
    }

    @MessageMapping("/chat/direct/typing")
    fun typing(@Payload directTyping: DirectMessageTyping, message: Message<*>) {
        val jwtToken = CustomSecurityContext.extractJwtTokenForStomp(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directTyping.toDomain(userId)
        directService.typing(directMessage)
    }

    @MessageMapping("/chat/direct/edit")
    fun edit(@Payload directEdit: DirectMessageEdit, message: Message<*>) {
        val jwtToken = CustomSecurityContext.extractJwtTokenForStomp(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directEdit.toDomain(userId)
        directService.edit(directMessage)
    }

    @MessageMapping("/chat/direct/delete")
    fun sendDeleteMessage(@Payload directDelete: DirectMessageDelete, message: Message<*>) {
        val jwtToken = CustomSecurityContext.extractJwtTokenForStomp(message)
        val userId = jwtTokenProvider.extract(jwtToken)
        val directMessage = directDelete.toDomain(userId)
        directService.delete(directMessage)
    }

    @ResponseBody
    @PostMapping("/file")
    fun uploadFile(@ModelAttribute fileRequest: FileRequest, servletRequest: HttpServletRequest): SuccessResponse<FileUploadResponse> {
        val jwtToken = CustomSecurityContext.extractJwtTokenForHttp(servletRequest)
        val userId = jwtTokenProvider.extract(jwtToken)
        val response = directService.upload(fileRequest, userId)
        return SuccessResponse.created(response)
    }
}
