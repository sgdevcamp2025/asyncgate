package com.asyncgate.chat_server.kafka

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.entity.DirectMessageEntity
import com.asyncgate.chat_server.entity.DirectMessageId
import com.asyncgate.chat_server.repository.DirectMessageRepository
import com.asyncgate.chat_server.support.utility.UUIDGenerator
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MessageSender(
    private val kafkaTemplateForDirectMessage: KafkaTemplate<String, DirectMessage>,
    private val directMessageRepository: DirectMessageRepository,
) {

    @Transactional
    fun send(topic: String, message: DirectMessage) {
        val domainMessage = directMessageRepository.save(message.toEntity())
        val key = domainMessage.channelId
        kafkaTemplateForDirectMessage.send(topic, key, domainMessage)
    }
}

fun DirectMessage.toEntity(): DirectMessageEntity {
    return DirectMessageEntity(
        id = DirectMessageId(id ?: UUIDGenerator.generate()),
        channelId = channelId,
        userId = userId,
        profileImage = profileImage,
        read = read,
        name = name,
        content = content,
        thumbnail = thumbnail,
        localDateTime = localDateTime,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent
    )
}

fun DirectMessageEntity.toDomain(): DirectMessage {
    return DirectMessage(
        id = id.value,
        channelId = channelId,
        userId = userId,
        profileImage = profileImage,
        read = read,
        name = name,
        content = content,
        thumbnail = thumbnail,
        localDateTime = localDateTime,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent
    )
}
