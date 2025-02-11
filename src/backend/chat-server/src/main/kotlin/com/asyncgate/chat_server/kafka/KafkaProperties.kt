package com.asyncgate.chat_server.kafka

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka")
class KafkaProperties {
    lateinit var bootstrapServers: String
    lateinit var topic: TopicProperties
    lateinit var consumer: ConsumerProperties

    class TopicProperties {
        lateinit var directMessage: String
        lateinit var readStatus: String
    }

    class ConsumerProperties {
        lateinit var groupId: GroupIdProperties

        class GroupIdProperties {
            lateinit var direct: String
            lateinit var readStatus: String
        }
    }
}
