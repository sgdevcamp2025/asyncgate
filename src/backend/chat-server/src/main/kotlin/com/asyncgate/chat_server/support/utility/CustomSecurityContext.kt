package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val JWT_HEADER = "Sec-WebSocket-Protocol"

    // Sec-WebSocket-Protocol 헤더에서 두 번째 값 (JWT 토큰)만 추출
    private fun parseJwtToken(headerValue: String): String? {
        val parts = headerValue.split(",").map { it.trim() }
        if (parts.size < 2) return null
        return parts[1]
    }

    fun extractJwtTokenForStomp(message: Message<*>): String {
        val accessor = StompHeaderAccessor.wrap(message)
        val headerValue = accessor.getFirstNativeHeader(JWT_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        return parseJwtToken(headerValue)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
    }

    fun extractJwtTokenForHttp(request: HttpServletRequest): String {
        val headerValue = request.getHeader(JWT_HEADER)
            ?: throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        return headerValue.trim()
    }
}
