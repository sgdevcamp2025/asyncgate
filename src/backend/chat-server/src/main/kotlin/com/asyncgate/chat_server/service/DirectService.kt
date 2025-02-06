package com.asyncgate.chat_server.service


import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.kafka.KafkaTopicProperties
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.repository.ReadStatusRepository
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface DirectService {
    fun send(directMessage: DirectMessage)
    fun updateReadStatus(readStatus: ReadStatus)
}

@Service
class DirectServiceImpl(
    private val kafkaTemplateForDirectMessage: KafkaTemplate<String, DirectMessage>,
    private val kafkaTemplateForReadStatus: KafkaTemplate<String, ReadStatus>,
    private val readStatusRepository: ReadStatusRepository,
    private val topicProperties: KafkaTopicProperties,
    private val directMessageRepository: DirectMessageRepository,
) : DirectService {

    @Transactional
    override fun send(directMessage: DirectMessage) {
        val key = directMessage.channelId
        kafkaTemplateForDirectMessage.send(topicProperties.directChat, key, directMessage)
        directMessageRepository.save(directMessage)
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
