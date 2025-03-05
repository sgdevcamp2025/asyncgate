package com.asyncgate.signaling_server.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class ICEServerConfig {

    @Value("${stun.server.urls}")
    private String stunServer;

    @Value("${turn.server.urls}")
    private String turnServer;

    @Value("${turn.server.username}")
    private String turnUsername;

    @Value("${turn.server.credential}")
    private String turnPassword;

    // TURN 서버 URL 반환
    public String getTurnUrl() {
        return String.format("turn:%s", turnServer);
    }

    public String getStunUrl() {
        return String.format("stun:%s", stunServer);
    }
}