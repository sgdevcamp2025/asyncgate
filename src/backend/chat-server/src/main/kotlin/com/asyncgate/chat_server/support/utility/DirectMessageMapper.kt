package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.entity.DirectMessageEntity

fun DirectMessage.toEntity(): DirectMessageEntity {
    return DirectMessageEntity(
        id = id ?: UUIDGenerator.generate(),
        channelId = channelId,
        userId = userId,
        profileImage = profileImage,
        read = read,
        name = name,
        content = content,
        thumbnail = thumbnail,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent
    )
}

fun DirectMessageEntity.toDomain(): DirectMessage {
    return DirectMessage(
        id = id,
        channelId = channelId,
        userId = userId,
        profileImage = profileImage,
        read = read,
        name = name,
        content = content,
        thumbnail = thumbnail,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
