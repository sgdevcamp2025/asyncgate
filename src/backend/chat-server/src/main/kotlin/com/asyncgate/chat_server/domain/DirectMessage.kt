package com.asyncgate.chat_server.domain

import java.time.LocalDateTime

class DirectMessage(
    val id: String? = null,

    val channelId: String,
    val userId: String,
    val profileImage: String,

    val read: Map<Long, Boolean>? = null,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,

    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)

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
