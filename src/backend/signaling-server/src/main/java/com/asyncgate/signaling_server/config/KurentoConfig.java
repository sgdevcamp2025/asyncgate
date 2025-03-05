package com.asyncgate.signaling_server.config;

import com.asyncgate.signaling_server.infrastructure.client.MemberServiceClient;
import com.asyncgate.signaling_server.infrastructure.utility.WebClientUtil;
import com.asyncgate.signaling_server.security.filter.FilterChannelInterceptor;
import com.asyncgate.signaling_server.security.filter.WebSocketHandshakeInterceptor;
import com.asyncgate.signaling_server.security.utility.JsonWebTokenUtil;
import com.asyncgate.signaling_server.signaling.KurentoManager;
import com.asyncgate.signaling_server.support.handler.KurentoHandler;
import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import org.kurento.client.KurentoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class KurentoConfig implements WebSocketMessageBrokerConfigurer {

    @Value("${kms.url}")
    private String kmsUrl;

    private final FilterChannelInterceptor filterChannelInterceptor;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    @Lazy
    private final SimpMessagingTemplate messagingTemplate;
    private final WebClientUtil webClientUtil;

    public KurentoConfig(FilterChannelInterceptor filterChannelInterceptor,
                         WebSocketHandshakeInterceptor webSocketHandshakeInterceptor,
                         @Lazy SimpMessagingTemplate messagingTemplate,
                         WebClientUtil webClientUtil) {
        this.filterChannelInterceptor = filterChannelInterceptor;
        this.webSocketHandshakeInterceptor = webSocketHandshakeInterceptor;
        this.messagingTemplate = messagingTemplate;
        this.webClientUtil = webClientUtil;
    }

    @Bean
    public KurentoClient kurentoClient() {
        return KurentoClient.create(kmsUrl);
    }

    @Bean
    public MemberServiceClient memberServiceClient() {
        return new MemberServiceClient(webClientUtil);
    }

    @Bean
    public KurentoManager kurentoManager(KurentoClient kurentoClient, MemberServiceClient memberServiceClient) {
        return new KurentoManager(kurentoClient, memberServiceClient, messagingTemplate);
    }

    @Bean
    public KurentoHandler kurentoHandler(KurentoManager kurentoManager) {
        return new KurentoHandler(kurentoManager);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/signal")
                .setAllowedOriginPatterns("*")
                .addInterceptors(webSocketHandshakeInterceptor);
    }

    /*
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/kafka");
        registry.enableSimpleBroker("/topic/");
    }
     */

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(filterChannelInterceptor);
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(32768);
        container.setMaxBinaryMessageBufferSize(32768);
        return container;
    }
}