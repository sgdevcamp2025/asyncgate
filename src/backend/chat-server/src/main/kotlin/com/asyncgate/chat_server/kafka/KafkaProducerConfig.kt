package com.asyncgate.chat_server.kafka

import com.asyncgate.chat_server.controller.FileUploadResponse
import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.LoginSession
import com.asyncgate.chat_server.domain.ReadStatus
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafka
@Configuration
class KafkaProducerConfig(
    private val kafkaProperties: KafkaProperties,
) {

    private fun producerConfigurations(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java
        )
    }

    @Bean
    fun kafkaTemplateForDirect(): KafkaTemplate<String, DirectMessage> {
        return KafkaTemplate(producerFactoryForDirect())
    }

    fun producerFactoryForDirect(): ProducerFactory<String, DirectMessage> {
        return DefaultKafkaProducerFactory(producerConfigurations())
    }

    @Bean
    fun kafkaTemplateForReadStatus(): KafkaTemplate<String, ReadStatus> {
        return KafkaTemplate(producerFactoryForReadStatus())
    }

    fun producerFactoryForReadStatus(): ProducerFactory<String, ReadStatus> {
        return DefaultKafkaProducerFactory(producerConfigurations())
    }

    @Bean
    fun kafkaTemplateForUpload(): KafkaTemplate<String, FileUploadResponse> {
        return KafkaTemplate(producerFactoryForUpload())
    }

    fun producerFactoryForUpload(): ProducerFactory<String, FileUploadResponse> {
        return DefaultKafkaProducerFactory(producerConfigurations())
    }

    @Bean
    fun kafkaTemplateForLoginSession(): KafkaTemplate<String, LoginSession> {
        return KafkaTemplate(producerFactoryForLoginSession())
    }

    fun producerFactoryForLoginSession(): ProducerFactory<String, LoginSession> {
        return DefaultKafkaProducerFactory(producerConfigurations())
    }
}
