package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val AUTHORIZATION_HEADER = "Authorization"

    // Authorization 헤더에서 "Bearer " 접두어를 제거한 후 JWT 토큰을 반환
    private fun parseJwtToken(headerValue: String): String {
        return try {
            val token = headerValue.trim()
            if (token.startsWith("Bearer ", ignoreCase = true)) {
                token.substring(7).trim()
            } else {
                throw ChatServerException(FailType.JWT_INVALID_TOKEN)
            }
        } catch (e: Exception) {
            throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        }
    }

    fun extractJwtTokenForStomp(message: Message<*>): String {
        val accessor = StompHeaderAccessor.wrap(message)
        val headerValue = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)

        println("headerValue = $headerValue")

        return parseJwtToken(headerValue)
    }

    fun extractJwtTokenForHttp(request: HttpServletRequest): String {
        val headerValue = request.getHeader(AUTHORIZATION_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)

        return parseJwtToken(headerValue)
    }

    fun extractSessionIdForStomp(message: Message<*>): String {
        val accessor = StompHeaderAccessor.wrap(message)
        return accessor.sessionId ?: throw ChatServerException(FailType.SESSION_ID_NOT_FOUND)
    }
}
