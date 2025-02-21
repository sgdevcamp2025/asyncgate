package com.asyncgate.configserver.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Configuration
public class ConfigPrinter {

    private final EnvironmentRepository environmentRepository;
    private final TextEncryptor textEncryptor;

    public ConfigPrinter(EnvironmentRepository environmentRepository, TextEncryptor textEncryptor) {
        this.environmentRepository = environmentRepository;
        this.textEncryptor = textEncryptor;
    }

    @Bean
    public ApplicationRunner printConfigs() {
        return args -> {
            // config 폴더에 있는 모든 서비스 리스트
            String[] services = {
                    "apigateway-server", "chat-server", "guild-server",
                    "notification-server", "signaling_server", "state-server", "user-server"
            };

            for (String service : services) {
                Environment env = environmentRepository.findOne(service, "default", "main");

                System.out.println("======== " + service + " ========");
                env.getPropertySources().forEach(propertySource -> {
                    propertySource.getSource().forEach((key, value) -> {
                        if (value instanceof String && ((String) value).startsWith("{cipher}")) {
                            try {
                                String decrypted = textEncryptor.decrypt(((String) value).substring(8));
                                System.out.println(key + ": " + decrypted);
                            } catch (Exception e) {
                                System.out.println(key + ": [DECRYPTION FAILED]");
                            }
                        } else {
                            System.out.println(key + ": " + value);
                        }
                    });
                });
                System.out.println("===============================");
            }
        };
    }
}

