package com.asyncgate.chat_server.kafka

import com.asyncgate.chat_server.controller.FileUploadResponse
import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.LoginSession
import com.asyncgate.chat_server.domain.ReadStatus
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    private val kafkaProperties: KafkaProperties,
) {
    private fun baseConsumerConfigurations(): Map<String, Any> {
        return mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to "latest"
        )
    }

    /**
     * ✅ 1. Direct Message 관련 설정
     */
    private fun directMessageConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.directMessage
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
    fun directFactory(): ConcurrentKafkaListenerContainerFactory<String, DirectMessage> {
        return ConcurrentKafkaListenerContainerFactory<String, DirectMessage>().apply {
            this.consumerFactory = consumerFactoryForDirect()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    /**
     * ✅ 2. Read Status 관련 설정
     */
    private fun readStatusConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.readStatus
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
    fun readStatusFactory(): ConcurrentKafkaListenerContainerFactory<String, ReadStatus> {
        return ConcurrentKafkaListenerContainerFactory<String, ReadStatus>().apply {
            this.consumerFactory = consumerFactoryForReadStatus()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    /**
     * ✅ 3. Direct Action 관련 설정 (DirectMessage와 같은 타입 사용)
     */
    private fun directActionConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.directAction
        )
    }

    @Bean
    fun consumerFactoryForDirectAction(): ConsumerFactory<String, DirectMessage> {
        val deserializer = JsonDeserializer(DirectMessage::class.java).apply {
            this.addTrustedPackages("*")
        }
        return DefaultKafkaConsumerFactory(
            directActionConsumerConfigurations(),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun directActionFactory(): ConcurrentKafkaListenerContainerFactory<String, DirectMessage> {
        return ConcurrentKafkaListenerContainerFactory<String, DirectMessage>().apply {
            this.consumerFactory = consumerFactoryForDirectAction()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    /**
     * ✅ 4. File Upload 관련 설정
     */
    private fun uploadConsumerConfigurations(): Map<String, Any> {
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.directUpload
        )
    }

    @Bean
    fun consumerFactoryForUpload(): ConsumerFactory<String, FileUploadResponse> {
        val deserializer = JsonDeserializer(FileUploadResponse::class.java).apply {
            this.addTrustedPackages("*")
        }
        return DefaultKafkaConsumerFactory(
            uploadConsumerConfigurations(),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun uploadFactory(): ConcurrentKafkaListenerContainerFactory<String, FileUploadResponse> {
        return ConcurrentKafkaListenerContainerFactory<String, FileUploadResponse>().apply {
            this.consumerFactory = consumerFactoryForUpload()
            this.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }

    /**
     * ✅ 4. Login Session 관련 설정
     */

    private fun loginSessionConsumerConfigurations(): Map<String, Any> {
        // groupId를 "loginSession" 같은 이름으로 설정한다고 가정
        return baseConsumerConfigurations() + mapOf(
            ConsumerConfig.GROUP_ID_CONFIG to kafkaProperties.consumer.groupId.loginSession
        )
    }

    @Bean
    fun consumerFactoryForLoginSession(): ConsumerFactory<String, LoginSession> {
        val deserializer = JsonDeserializer(LoginSession::class.java).apply {
            addTrustedPackages("*")
        }
        return DefaultKafkaConsumerFactory(
            loginSessionConsumerConfigurations(),
            StringDeserializer(),
            deserializer
        )
    }

    @Bean
    fun loginSessionFactory(): ConcurrentKafkaListenerContainerFactory<String, LoginSession> {
        return ConcurrentKafkaListenerContainerFactory<String, LoginSession>().apply {
            consumerFactory = consumerFactoryForLoginSession()
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        }
    }
}
