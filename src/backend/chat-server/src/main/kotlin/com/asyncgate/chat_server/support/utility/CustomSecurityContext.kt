package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val JWT_HEADER = "Sec-WebSocket-Protocol"

    // Bearer 접두어가 붙은 JWT 토큰만 추출. 없으면 null 반환.
    private fun parseJwtToken(headerValue: String): String? {
        val token = headerValue.trim()
        return if (token.startsWith("Bearer ")) {
            token.removePrefix("Bearer ").trim()
        } else {
            null
        }
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
