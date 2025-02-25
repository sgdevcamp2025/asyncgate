package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val AUTHORIZATION_HEADER = "Authorization"

    private fun parseJwtToken(headerValue: String): String? {
        val parts = headerValue.split(",").map { it.trim() }
        if (parts.size < 2) return null
        var token = parts[1]
        if (token.startsWith("Bearer ", ignoreCase = true)) {
            token = token.substring(7)
        }
        return token
    }

    fun extractJwtTokenForStomp(message: Message<*>): String {
        val accessor = StompHeaderAccessor.wrap(message)
        val headerValue = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        return parseJwtToken(headerValue)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
    }

    fun extractJwtTokenForHttp(request: HttpServletRequest): String {
        val headerValue = request.getHeader(AUTHORIZATION_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        return headerValue.trim()
    }
}
