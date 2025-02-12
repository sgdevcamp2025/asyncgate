package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType

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
) {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            id = id,
            channelId = channelId,
            userId = userId,
            type = DirectMessageType.DELETE
        )
    }
}

data class DirectMessageTyping(
    val channelId: String,
    val content: String,
    val name: String,
) {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            channelId = channelId,
            userId = userId,
            name = name,
            type = DirectMessageType.TYPING
        )
    }
}
