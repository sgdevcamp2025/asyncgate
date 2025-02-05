package com.asyncgate.chat_server.kafka

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka.topic")
class KafkaTopicProperties {
    lateinit var directChat: String
    lateinit var readStatus: String
}
