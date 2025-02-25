package com.asyncgate.chat_server.filter

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
     * Sec-WebSocket-Protocol 헤더 값은 "v10.stomp, <JWT 토큰>" 형태로 온다고 가정.
     * 첫 번째 값는 "v10.stomp", 두 번째 값은 JWT 토큰을 반환한다.
     */
    private fun splitProtocolHeader(headerValue: String?): Pair<String, String>? {
        if (headerValue.isNullOrBlank()) return null
        val parts = headerValue.split(",").map { it.trim() }
        if (parts.size < 2) return null
        if (parts[0] != "v10.stomp") return null
        return Pair(parts[0], parts[1])
    }

    override fun beforeHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        attributes: MutableMap<String, Any>,
    ): Boolean {
        println("✅ WebSocket Handshake - JWT 검증 시작")
        val headers = request.headers
        println("headers.size = ${headers.size}")
        for ((key, value) in headers) {
            println("header = $key : $value")
        }

        val protocols = headers["Sec-WebSocket-Protocol"]
        if (protocols.isNullOrEmpty()) {
            println("❌ STOMP 프로토콜 없음: WebSocket 연결 거부")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }

        // 클라이언트가 보낸 헤더의 첫 번째 값 예: "v10.stomp, <JWT 토큰>"
        val rawProtocol = protocols[0]
        val pair = splitProtocolHeader(rawProtocol)
        if (pair == null) {
            println("❌ 형식 오류: 헤더가 'v10.stomp, <JWT>' 형태가 아님")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }
        val (_, jwtToken) = pair

        if (!jwtTokenProvider.validate(jwtToken)) {
            println("❌ WebSocket Handshake 실패: 유효하지 않은 JWT 토큰")
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            response.headers["WWW-Authenticate"] =
                "Bearer error=\"invalid_token\", error_description=\"invalid JWT token\""
            return false
        }
        val userId = jwtTokenProvider.extract(jwtToken)
        println("✅ WebSocket Handshake 성공 - userId: $userId")
        response.headers.remove("Sec-WebSocket-Protocol")

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
