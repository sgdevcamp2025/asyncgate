package com.asyncgate.chat_server.filter

import com.asyncgate.chat_server.exception.ChatServerException
import org.springframework.http.HttpStatus
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.server.HandshakeInterceptor

@Component
class JwtHandshakeInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
) : HandshakeInterceptor {

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>,
    ): Boolean {
        println("✅ WebSocket Handshake - JWT 검증 시작")

        val headers = request.headers
        val jwtToken = headers.getFirst("jwt-token")

        if (jwtToken.isNullOrBlank()) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            response.headers["WWW-Authenticate"] = "Bearer error=\"invalid_token\", error_description=\"not found JWT token\""
            return false
        }

        try {
            if (!jwtTokenProvider.validate(jwtToken)) {
                println("❌ WebSocket Handshake 실패: 유효하지 않은 JWT 토큰")
                response.setStatusCode(HttpStatus.UNAUTHORIZED)
                response.headers["WWW-Authenticate"] = "Bearer error=\"invalid_token\", error_description=\"invalid JWT token\""
                return false
            }

            val userId = jwtTokenProvider.extract(jwtToken)
            println("✅ WebSocket Handshake 성공 - userId: $userId")

            return true
        } catch (e: ChatServerException) {
            println("❌ WebSocket Handshake 실패: ${e.failType.message}")
            response.setStatusCode(e.failType.status)
            response.headers["WWW-Authenticate"] = "Bearer error=\"invalid_token\", error_description=\"invalid JWT token\""
            return false
        }
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?,
    ) {
    }
}
