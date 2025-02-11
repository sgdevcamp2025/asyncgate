package com.asyncgate.chat_server.kafka

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    private val kafkaProperties: KafkaProperties // ✅ KafkaProperties 주입
) {
    private fun baseConsumerConfigurations(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest"
        )
    }

    private fun directMessageConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.direct
        )
    }

    private fun readStatusConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.readStatus
        )
    }

    @Bean
    fun consumerFactoryForDirect(): ConsumerFactory<String, DirectMessage> {
        val deserializer = JsonDeserializer(DirectMessage::class.java).apply {
            this.addTrustedPackages("*")
        }

        return DefaultKafkaConsumerFactory(
            directMessageConsumerConfigurations(),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun consumerFactoryForReadStatus(): ConsumerFactory<String, ReadStatus> {
        val deserializer = JsonDeserializer(ReadStatus::class.java).apply {
            this.addTrustedPackages("*")
        }

        return DefaultKafkaConsumerFactory(
            readStatusConsumerConfigurations(),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun directFactory(): ConcurrentKafkaListenerContainerFactory<String, DirectMessage> {
        return ConcurrentKafkaListenerContainerFactory<String, DirectMessage>().apply {
            this.consumerFactory = consumerFactoryForDirect()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    @Bean
    fun readStatusFactory(): ConcurrentKafkaListenerContainerFactory<String, ReadStatus> {
        return ConcurrentKafkaListenerContainerFactory<String, ReadStatus>().apply {
            this.consumerFactory = consumerFactoryForReadStatus()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }
}
