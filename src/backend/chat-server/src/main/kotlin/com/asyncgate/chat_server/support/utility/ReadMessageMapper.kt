package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.entity.ReadStatusEntity

fun ReadStatus.toEntity(): ReadStatusEntity {
    return ReadStatusEntity(
        id = id ?: IdGenerator.generate(),
        userId = userId,
        channelId = channelId,
        lastReadMessageId = lastReadMessageId
    )
}

fun ReadStatusEntity.toDomain(): ReadStatus {
    return ReadStatus(
        id = id,
        userId = userId,
        channelId = channelId,
        lastReadMessageId = lastReadMessageId,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
