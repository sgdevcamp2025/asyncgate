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

    /**
     * 예: headerValue = "v10.stomp, eyJhbGciOiJI..."
     * parts[0] = "v10.stomp"
     * parts[1] = "eyJhbGciOiJI..."
     */
    private fun splitProtocolHeader(headerValue: String?): Pair<String, String>? {
        if (headerValue.isNullOrBlank()) return null
        val parts = headerValue.split(",").map { it.trim() }
        if (parts.size < 2) return null
        return Pair(parts[0], parts[1]) // (v10.stomp, <JWT>)
    }

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>,
    ): Boolean {
        println("✅ WebSocket Handshake - JWT 검증 시작")

        val headers = request.headers
        val protocols = headers["Sec-WebSocket-Protocol"]
        if (protocols.isNullOrEmpty()) {
            println("❌ STOMP 프로토콜 없음: WebSocket 연결 거부")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }

        // 예: protocols[0] = "v10.stomp, eyJ..."
        val rawProtocol = protocols[0]
        val (stompValue, jwtToken) = splitProtocolHeader(rawProtocol) ?: run {
            println("❌ 형식 오류: v10.stomp, <JWT> 형태가 아님")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }

        // 첫 번째 값이 "v10.stomp"인지 확인
        if (stompValue != "v10.stomp") {
            println("❌ STOMP 프로토콜 없음")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }

        // JWT 검증
        try {
            if (!jwtTokenProvider.validate(jwtToken)) {
                println("❌ WebSocket Handshake 실패: 유효하지 않은 JWT 토큰")
                response.setStatusCode(HttpStatus.UNAUTHORIZED)
                return false
            }
            val userId = jwtTokenProvider.extract(jwtToken)
            println("✅ WebSocket Handshake 성공 - userId: $userId")
        } catch (e: ChatServerException) {
            println("❌ WebSocket Handshake 실패: ${e.failType.message}")
            response.setStatusCode(e.failType.status)
            return false
        }

        // ─────────────────────────────────────────
        // 응답 헤더에 두 줄로 넣기
        // ─────────────────────────────────────────
        // 0: "v10.stomp"
        // 1: "<JWT 토큰>"
        response.headers.remove("Sec-WebSocket-Protocol") // 혹시 남아있을 값을 제거
        response.headers.add("Sec-WebSocket-Protocol", jwtToken) // 두 번째

        return true
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?,
    ) {
    }
}
