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

        println("headers.size = ${headers.size}")
        for ((key, value) in headers) {
            println("header = $key : $value")
        }

        // 클라이언트가 STOMP 프로토콜로 "v10.stomp"를 사용했는지 확인
        val protocols = headers["Sec-WebSocket-Protocol"]
        if (protocols.isNullOrEmpty() || !protocols.contains("v10.stomp")) {
            println("❌ STOMP 프로토콜 없음: WebSocket 연결 거부")
            response.setStatusCode(HttpStatus.BAD_REQUEST)
            return false
        }

        // JWT 토큰은 Authorization 헤더에서 추출
        val rawToken = headers["Authorization"]?.firstOrNull()
        if (rawToken.isNullOrBlank()) {
            println("❌ JWT 검증 실패: Authorization 헤더 없음")
            response.setStatusCode(HttpStatus.UNAUTHORIZED)
            response.headers["WWW-Authenticate"] =
                "Bearer error=\"invalid_token\", error_description=\"not found JWT token\""
            return false
        }
        // Bearer 접두어 제거
        val jwtToken = if (rawToken.startsWith("Bearer ")) {
            rawToken.removePrefix("Bearer ").trim()
        } else {
            rawToken.trim()
        }

        try {
            if (!jwtTokenProvider.validate(jwtToken)) {
                println("❌ WebSocket Handshake 실패: 유효하지 않은 JWT 토큰")
                response.setStatusCode(HttpStatus.UNAUTHORIZED)
                response.headers["WWW-Authenticate"] =
                    "Bearer error=\"invalid_token\", error_description=\"invalid JWT token\""
                return false
            }

            val userId = jwtTokenProvider.extract(jwtToken)
            println("✅ WebSocket Handshake 성공 - userId: $userId")

            // 클라이언트에게 지원하는 STOMP 프로토콜을 응답으로 설정
            response.headers.add("Sec-WebSocket-Protocol", "v10.stomp")
            return true
        } catch (e: ChatServerException) {
            println("❌ WebSocket Handshake 실패: ${e.failType.message}")
            response.setStatusCode(e.failType.status)
            response.headers["WWW-Authenticate"] =
                "Bearer error=\"invalid_token\", error_description=\"invalid JWT token\""
            return false
        }
    }

    override fun afterHandshake(
        request: ServerHttpRequest,
        response: ServerHttpResponse,
        wsHandler: WebSocketHandler,
        exception: Exception?,
    ) {
        // Handshake 이후 추가 처리가 필요하면 여기에 작성
    }
}
