package com.asyncgate.chat_server.config

import com.asyncgate.chat_server.filter.FilterChannelInterceptor
import com.asyncgate.chat_server.filter.JwtHandshakeInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.ChannelRegistration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig(
    private val filterChannelInterceptor: FilterChannelInterceptor,
    private val jwtHandshakeInterceptor: JwtHandshakeInterceptor,
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint("/asyncgate-chat")
            .setAllowedOriginPatterns("*")
            .addInterceptors(jwtHandshakeInterceptor)
            .withSockJS()
    }

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.setApplicationDestinationPrefixes("/kafka")
        registry.enableSimpleBroker("/topic/")
    }

    override fun configureClientInboundChannel(registration: ChannelRegistration) {
        registration.interceptors(filterChannelInterceptor)
    }
}
