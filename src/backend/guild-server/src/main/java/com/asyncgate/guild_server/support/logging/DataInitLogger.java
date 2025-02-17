package com.asyncgate.guild_server.support.logging;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataInitLogger {

    private static final Logger logger = LoggerFactory.getLogger(DataInitLogger.class);

    private final Environment environment;

    public DataInitLogger(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void dataInit() {
        // 특정 설정 값을 출력
        String serverPort = environment.getProperty("server.port");
        String configServerUri = environment.getProperty("spring.cloud.config.uri");
        String applicationName = environment.getProperty("spring.application.name");

        logger.info("🔍 API Gateway Configuration Loaded:");
        logger.info("✅ Server Port: {}", serverPort);
        logger.info("✅ Config Server URI: {}", configServerUri);
        logger.info("✅ Application Name: {}", applicationName);

        // 전체 설정 출력 (필요하면 사용)
        logger.info("📌 전체 환경 변수: {}", environment);
    }
}