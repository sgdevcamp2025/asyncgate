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
