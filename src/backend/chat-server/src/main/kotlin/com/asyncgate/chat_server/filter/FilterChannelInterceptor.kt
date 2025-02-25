package com.asyncgate.chat_server.filter

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException

@Component
class FilterChannelInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
) : ChannelInterceptor {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(FilterChannelInterceptor::class.java)
        private const val WEB_SOCKET_PROTOCOL_HEADER = "Sec-WebSocket-Protocol"
    }

    private fun extractToken(protocolHeader: String?): String? {
        if (protocolHeader == null) return null
        val parts = protocolHeader.split(",").map { it.trim() }
        if (!parts.contains("v10.stomp")) return null
        return parts.find { it.startsWith("Bearer ") }?.removePrefix("Bearer ")?.trim()
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val headerAccessor = StompHeaderAccessor.wrap(message)
        log.info("📥 [STOMP] Command: ${headerAccessor.command}, sessionId: ${headerAccessor.sessionId}")

        if (StompCommand.CONNECT == headerAccessor.command) {
            // 여기서는 Authorization 헤더가 없으므로, Sec-WebSocket-Protocol에서 JWT 추출
            val rawProtocol = headerAccessor.getFirstNativeHeader(WEB_SOCKET_PROTOCOL_HEADER)
            log.info("🔑 [STOMP] Raw Protocol Header: $rawProtocol")
            val jwtToken = extractToken(rawProtocol)
            if (jwtToken.isNullOrBlank()) {
                log.error("🚨 [STOMP] Access Token is missing or improperly formatted!")
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access token is missing")
            }
            if (!jwtTokenProvider.validate(jwtToken)) {
                log.error("🚨 [STOMP] Access Token validation failed!")
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
            log.info("✅ [STOMP] CONNECT 요청 처리 완료")
        }
        return message
    }

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {
        val accessor = StompHeaderAccessor.wrap(message)
        log.info("📡 [STOMP] Command: ${accessor.command}, sessionId: ${accessor.sessionId}, sent: $sent")

        when (accessor.command) {
            StompCommand.CONNECT -> {
                log.info("✅ [STOMP] CONNECT 성공 - sessionId: ${accessor.sessionId}")
                handleConnect(accessor)
                log.info("✅ [STOMP] CONNECTED 프레임이 자동으로 반환되었는지 확인 필요")
                log.info("🔎 [STOMP] CONNECTED 프레임 헤더: ${accessor.messageHeaders}")
                accessor.messageHeaders.forEach { header ->
                    println("messageHeader = $header")
                }
            }
            StompCommand.DISCONNECT -> {
                log.info("🔌 [STOMP] DISCONNECT 요청 - sessionId: ${accessor.sessionId}")
                handleDisconnect(accessor)
            }
            else -> {}
        }
    }

    private fun handleDisconnect(accessor: StompHeaderAccessor) {
        log.info("🔌 [STOMP] WebSocket 연결 해제 - sessionId: ${accessor.sessionId}")
    }

    private fun handleConnect(accessor: StompHeaderAccessor) {
        val currentSessionId = accessor.sessionId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "not session now")
        // JWT 토큰는 Sec-WebSocket-Protocol에서 추출
        val rawProtocol = accessor.getFirstNativeHeader(WEB_SOCKET_PROTOCOL_HEADER)
        val jwtToken = extractToken(rawProtocol)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "jwt-token is missing")
        val currentUserId = jwtTokenProvider.extract(jwtToken)

        val loginSessionRequest = LoginSessionRequest(
            type = LoginType.LOGIN,
            sessionId = currentSessionId,
            userId = currentUserId
        )
//
//        // ToDo 상태관리 서버에 로그인 전달
//        val guildIds = guildClient.getGuildIds(jwtToken)
        val stateRequest = StateRequest(
            StatusType.CONNECT,
            userId = currentUserId
//            guildIds = guildIds
        )

        // 시그널링 서버에 전달
        //                messageSender.signaling(stateTopic, stateRequest)
        // 이후 상태 관리나 로그인을 처리할 수 있음
    }
}

data class LoginSessionRequest(
    var type: LoginType,
    val sessionId: String,
    val userId: String,
    val communityId: String? = null,
    val ids: List<Long>? = null,
) : java.io.Serializable {
    override fun toString(): String {
        return "LoginSessionRequest(type=$type, sessionId='$sessionId', userId='$userId', communityId=$communityId, ids=$ids)"
    }
}

enum class LoginType {
    LOGIN,
    LOGOUT,
}

data class StateRequest(
    val type: StatusType,
    val userId: String,
    val guildIds: List<String>? = null,
) : java.io.Serializable

enum class StatusType {
    CONNECT,
    DISCONNECT,
}
