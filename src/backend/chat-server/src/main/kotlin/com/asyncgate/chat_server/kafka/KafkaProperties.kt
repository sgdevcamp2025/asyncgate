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
        lateinit var directAction: String
        lateinit var readStatus: String
        lateinit var directUpload: String
        lateinit var loginSession: String
    }

    class ConsumerProperties {
        lateinit var groupId: GroupIdProperties

        class GroupIdProperties {
            lateinit var directMessage: String
            lateinit var directAction: String
            lateinit var readStatus: String
            lateinit var directUpload: String
            lateinit var loginSession: String
        }
    }
}
