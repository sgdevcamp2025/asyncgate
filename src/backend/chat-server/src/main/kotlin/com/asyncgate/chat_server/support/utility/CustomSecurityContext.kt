package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import jakarta.servlet.http.HttpServletRequest
import org.springframework.messaging.Message
import org.springframework.messaging.simp.stomp.StompHeaderAccessor

object CustomSecurityContext {
    private const val JWT_HEADER = "Sec-WebSocket-Protocol"

    // 헤더에서 콤마로 구분된 값 중 Bearer 접두어를 제거한 JWT 토큰을 반환
    private fun parseJwtToken(headerValue: String): String? {
        val parts = headerValue.split(",").map { it.trim() }
        // 보통 하나는 프로토콜(v10.stomp)이고, 하나는 "Bearer ..." 형태
        return parts.find { it.startsWith("Bearer ") }?.removePrefix("Bearer ")?.trim()
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
