package com.asyncgate.chat_server.domain

import org.springframework.data.annotation.Id
import java.time.LocalDateTime

data class DirectMessage(
    @Id
    val id: String? = null,

    val channelId: String,
    val userId: String,
    val profileImage: String,

    val read: Boolean = false,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val localDateTime: LocalDateTime = LocalDateTime.now(),

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null
)
