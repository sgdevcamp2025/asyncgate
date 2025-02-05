package com.asyncgate.chat_server.domain

import java.time.LocalDateTime

class DirectMessage(
    val id: String? = null,

    val channelId: String,
    val userId: String,
    val profileImage: String,

    val read: Boolean = false,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,

    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
