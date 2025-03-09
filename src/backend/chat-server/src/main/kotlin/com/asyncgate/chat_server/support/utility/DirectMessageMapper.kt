package com.asyncgate.chat_server.support.utility

import com.asyncgate.chat_server.controller.DirectPagingResponse
import com.asyncgate.chat_server.controller.DirectSingleResponse
import com.asyncgate.chat_server.controller.FileRequest
import com.asyncgate.chat_server.controller.FileUploadResponse
import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.entity.DirectMessageEntity
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import org.springframework.data.domain.Page

fun DirectMessage.toEntity(existingEntity: DirectMessageEntity? = null): DirectMessageEntity {
    return DirectMessageEntity(
        id = id,
        channelId = channelId,
        userId = userId,
        profileImage = profileImage,
        type = type,
        read = read,
        name = name,
        content = content,
        thumbnail = thumbnail,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent,
        isDeleted = existingEntity?.isDeleted ?: isDeleted
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
        updatedAt = updatedAt,
        type = type,
        isDeleted = isDeleted
    )
}

fun DirectMessage.toFileResponse(
    domain: DirectMessage,
    userId: String,
    fileRequest: FileRequest,
): FileUploadResponse {
    val response = FileUploadResponse(
        id = domain.id,
        name = domain.name ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR),
        domain.profileImage ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR),
        content = domain.content ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR),
        userId = userId,
        channelId = domain.channelId,
        type = fileRequest.fileType,
        time = domain.createdAt ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)
    )
    return response
}

fun DirectMessage.toSingleResponse(): DirectSingleResponse {
    return DirectSingleResponse(
        id = id,
        channelId = channelId,
        userId = userId,
        type = type,
        profileImage = profileImage,
        name = name,
        content = content,
        thumbnail = thumbnail,
        parentId = parentId,
        parentName = parentName,
        parentContent = parentContent,
        createdAt = createdAt ?: throw ChatServerException(FailType.X_DIRECT_INTERNAL_ERROR)
    )
}

fun Page<DirectMessage>.toPagingResponse(): DirectPagingResponse {
    return DirectPagingResponse(
        isFirst = isFirst,
        isLast = isLast,
        totalCount = totalElements.toInt(),
        totalPages = totalPages,
        currentPage = number + 1,
        pageSize = size,
        directMessages = content.map { it.toSingleResponse() }
    )
}
