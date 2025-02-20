package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val JWT_HEADER = "jwt-token"

    private const val AUTH_HEADER = "Authorization"
    private const val BEARER_PREFIX = "Bearer "

    fun extractJwtTokenForStomp(message: Message<*>): String {
        val accessor = StompHeaderAccessor.wrap(message)

        return accessor.getFirstNativeHeader(JWT_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
    }

    fun extractJwtTokenForHttp(request: HttpServletRequest): String {
        val authHeader = request.getHeader(AUTH_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)

        if (!authHeader.startsWith(BEARER_PREFIX)) {
            throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        }

        return authHeader.removePrefix(BEARER_PREFIX).trim()
    }
}
