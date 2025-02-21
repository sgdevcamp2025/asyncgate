package com.asyncgate.guild_server.support.logging;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Component
public class DataInitLogger {

    private static final Logger logger = LoggerFactory.getLogger(DataInitLogger.class);

    private final Environment environment;
    private final DataSource dataSource;

    public DataInitLogger(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void dataInit() {
        // 환경 변수 출력
        String serverPort = environment.getProperty("server.port");
        String configServerUri = environment.getProperty("spring.cloud.config.uri");
        String applicationName = environment.getProperty("spring.application.name");
        String dbUrl = environment.getProperty("spring.datasource.url");
        String dbUser = environment.getProperty("spring.datasource.username");

        logger.info("🔍 API Gateway Configuration Loaded:");
        logger.info("✅ Server Port: {}", serverPort);
        logger.info("✅ Config Server URI: {}", configServerUri);
        logger.info("✅ Application Name: {}", applicationName);
        logger.info("✅ DB URL (from properties): {}", dbUrl);
        logger.info("✅ DB User: {}", dbUser);

        // DataSource에서 실제 연결된 DB 정보 가져오기
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            logger.info("✅ Connected Database URL: {}", metaData.getURL());
            logger.info("✅ Connected Database User: {}", metaData.getUserName());
        } catch (Exception e) {
            logger.error("❌ Failed to retrieve DB connection info", e);
        }
    }
}
