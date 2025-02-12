package com.asyncgate.chat_server.domain

import java.time.LocalDateTime

class DirectMessage(
    val id: String? = null,

    val channelId: String,
    val userId: String,
    val type: DirectMessageType,

    val profileImage: String? = null,

    val read: Map<Long, Boolean>? = null,

    val name: String? = null,
    val content: String? = null,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,

    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)

enum class DirectMessageType {
    CREATE,
    EDIT,
    DELETE,
    TYPING,
}

data class DirectMessageCreate(
    val channelId: String,
    val profileImage: String,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,
) {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            channelId = channelId,
            userId = userId,
            type = DirectMessageType.CREATE,
            profileImage = profileImage,
            name = name,
            content = content,
            thumbnail = thumbnail,
            parentId = parentId,
            parentName = parentName,
            parentContent = parentContent
        )
    }
}

data class DirectMessageEdit(
    val id: String,
    val channelId: String,
    val name: String,
    val content: String,
) {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            channelId = channelId,
            userId = userId,
            type = DirectMessageType.EDIT,
            name = name,
            content = content
        )
    }
}

data class DirectMessageDelete(
    val channelId: String,
    val id: String,
)

data class DirectMessageTyping(
    val channelId: String,
    val content: String,
) {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            channelId = channelId,
            userId = userId,
            type = DirectMessageType.TYPING
        )
    }
}
