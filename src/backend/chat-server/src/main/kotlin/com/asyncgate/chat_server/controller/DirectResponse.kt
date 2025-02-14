package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessageType
import java.time.LocalDateTime

class FileUploadResponse(
    val id: String,
    val name: String,
    val profileImage: String,
    val content: String,
    val thumbnail: String? = null,
    val userId: String,
    val channelId: String,
    val type: DirectMessageType,
    val time: LocalDateTime,
)
