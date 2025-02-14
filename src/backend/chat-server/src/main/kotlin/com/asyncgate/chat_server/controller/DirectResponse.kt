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

data class DirectPagingResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int,
    val directMessages: List<DirectSingleResponse>,
)

data class DirectSingleResponse(
    val id: String,
    val channelId: String,
    val userId: String,
    val type: DirectMessageType,
    val profileImage: String?,
    val name: String?,
    val content: String?,
    val thumbnail: String?,
    val parentId: String?,
    val parentName: String?,
    val parentContent: String?,
    val createdAt: LocalDateTime,
)
