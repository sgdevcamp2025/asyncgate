package com.asyncgate.chat_server.filter

import com.asyncgate.chat_server.client.GuildClient
import org.springframework.beans.factory.annotation.Value
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
    private val guildClient: GuildClient,
) : ChannelInterceptor {

    @Value("\${spring.kafka.consumer.state-topic}")
    private lateinit var stateTopic: String

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*> {
        val headerAccessor = StompHeaderAccessor.wrap(message)
        if (StompCommand.CONNECT == headerAccessor.command) {
            val accessToken = headerAccessor.getFirstNativeHeader("jwt-token")
                ?: throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "access-token is missing")
            if (!jwtTokenProvider.validate(accessToken)) {
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
            }
        }
        return message
    }

    override fun postSend(message: Message<*>, channel: MessageChannel, sent: Boolean) {
        val accessor = StompHeaderAccessor.wrap(message)
        when (accessor.command) {
            StompCommand.CONNECT -> handleConnect(accessor)
            StompCommand.DISCONNECT -> handleDisConnect(accessor)
            else -> {}
        }
    }

    private fun handleDisConnect(accessor: StompHeaderAccessor) {
        val currentSessionId = accessor.sessionId
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "not session now")
        val jwtToken = accessor.getFirstNativeHeader("jwt-token")
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "jwt-token is missing")
        val currentUserId = jwtTokenProvider.extract(jwtToken)

        val logOutSessionRequest = LoginSessionRequest(
            type = LoginType.LOGOUT,
            sessionId = currentSessionId,
            userId = currentUserId
        )

        // ToDo
        // 상태관리 서버에 로그아웃 전달
        // 시그널링 서버에 전달
        val stateRequest = StateRequest(
            StatusType.DISCONNECT,
            userId = currentUserId
        )
        // directServiceImpl.signaling(stateTopic, stateRequest)
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

        // ToDo 상태관리 서버에 로그인 전달
        val guildIds = guildClient.getGuildIds(jwtToken)

        val stateRequest = StateRequest(
            StatusType.CONNECT,
            userId = currentUserId,
            guildIds = guildIds
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
