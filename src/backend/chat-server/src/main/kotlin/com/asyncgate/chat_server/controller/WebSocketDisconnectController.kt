package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.filter.JwtTokenProvider
import com.asyncgate.chat_server.filter.LoginSessionRequest
import com.asyncgate.chat_server.filter.LoginType
import com.asyncgate.chat_server.filter.StateRequest
import com.asyncgate.chat_server.filter.StatusType
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageHeaderAccessor
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class WebSocketDisconnectController(
    private val jwtTokenProvider: JwtTokenProvider,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(WebSocketDisconnectController::class.java)
    }
    
    @MessageMapping("/disconnect")
    fun handleDisconnect(message: Message<*>) {
        val accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor::class.java)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot retrieve header accessor")

        log.info("🔴 클라이언트가 명시적으로 DISCONNECT 요청을 보냈음!")

        // ✅ 세션 ID 가져오기
        val currentSessionId = accessor.sessionId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Session ID is missing")

        // ✅ JWT 토큰 가져오기
        val jwtToken = accessor.getFirstNativeHeader("jwt-token")
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT Token is missing")

        log.info("📌 받은 JWT Token: $jwtToken")
        log.info("📌 받은 세션 ID: $currentSessionId")

        // ✅ JWT 토큰에서 사용자 ID 추출
        val currentUserId = jwtTokenProvider.extract(jwtToken)

        val logOutSessionRequest = LoginSessionRequest(
            type = LoginType.LOGOUT,
            sessionId = currentSessionId,
            userId = currentUserId
        )

        // ✅ 상태관리 서버에 로그아웃 전달
        val stateRequest = StateRequest(
            StatusType.DISCONNECT,
            userId = currentUserId
        )

        // TODO: 상태관리 서버로 `stateRequest` 전송
    }
}
