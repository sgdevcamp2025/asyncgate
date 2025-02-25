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
import java.io.Serializable

@Component
class FilterChannelInterceptor(
    private val jwtTokenProvider: JwtTokenProvider,
) : ChannelInterceptor {

    /*
    @Value("\${spring.kafka.consumer.state-topic}")
    private lateinit var stateTopic: String
     */

    companion object {
        private val log: Logger = LoggerFactory.getLogger(FilterChannelInterceptor::class.java)
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val headerAccessor = StompHeaderAccessor.wrap(message)
        println("STOMP Command: ${headerAccessor.command}") 

        if (StompCommand.CONNECT == headerAccessor.command) {
            val accessToken = headerAccessor.getFirstNativeHeader("Sec-WebSocket-Protocol")
                ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "access-token is missing")
            if (!jwtTokenProvider.validate(accessToken)) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
            println("✅ STOMP CONNECT 요청 처리 완료") // 🔹 STOMP CONNECT 성공 로그 추가
        }

        return message
    }


    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {
        val accessor = StompHeaderAccessor.wrap(message)
        when (accessor.command) {
            StompCommand.CONNECT -> handleConnect(accessor)
            StompCommand.DISCONNECT -> handleDisconnect()
            else -> {}
        }
    }

    private fun handleDisconnect() {
        log.info("WebSocket 연결 해제")
    }

    private fun handleConnect(accessor: StompHeaderAccessor) {
        val currentSessionId = accessor.sessionId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "not session now")
        val jwtToken = accessor.getFirstNativeHeader("jwt-token")
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
    }
}

data class LoginSessionRequest(
    var type: LoginType,
    val sessionId: String,
    val userId: String,
    val communityId: String? = null,
    val ids: List<Long>? = null,
) : Serializable {
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
) : Serializable

enum class StatusType {
    CONNECT,
    DISCONNECT,
}
