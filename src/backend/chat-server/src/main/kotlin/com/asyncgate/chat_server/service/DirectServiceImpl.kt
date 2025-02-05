package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.kafka.KafkaTopicProperties
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.repository.ReadStatusRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DirectServiceImpl(
    private val kafkaTemplateForDirectMessage: KafkaTemplate<String, DirectMessage>,
    private val kafkaTemplateForReadStatus: KafkaTemplate<String, ReadStatus>,
    private val directMessageRepository: DirectMessageRepository,
    private val readStatusRepository: ReadStatusRepository,
    private val topicProperties: KafkaTopicProperties,
) : DirectService {

    @Transactional
    override fun send(message: DirectMessage) {
        val domainMessage = directMessageRepository.save(message)
        val key = domainMessage.channelId
        kafkaTemplateForDirectMessage.send(topicProperties.directChat, key, domainMessage)
    }

    @Transactional
    override fun updateReadStatus(readStatus: ReadStatus) {
        val key = readStatus.channelId

        val existingReadStatus = readStatusRepository.findByUserIdAndChannelId(
            userId = readStatus.userId,
            channelId = readStatus.channelId
        )

        if (existingReadStatus != null) {
            val lastReadMessageId = checkNotNull(existingReadStatus.lastReadMessageId) {
                "Logic error: lastReadMessageId should never be null for an existing read status"
            }
            existingReadStatus.updateLastReadMessageId(lastReadMessageId)
            readStatusRepository.save(existingReadStatus)
            kafkaTemplateForReadStatus.send(topicProperties.readStatus, key, existingReadStatus)
            return
        }

        val newReadStatus = ReadStatus.create(
            userId = readStatus.userId,
            channelId = readStatus.channelId
        )
        readStatusRepository.save(newReadStatus)
        kafkaTemplateForReadStatus.send(topicProperties.readStatus, key, newReadStatus)
    }
}
