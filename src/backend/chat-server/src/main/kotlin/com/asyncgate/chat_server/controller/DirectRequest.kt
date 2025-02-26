package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.springframework.web.multipart.MultipartFile
import java.io.Serializable

@JsonSerialize
@JsonDeserialize
data class DirectMessageCreate(
    val channelId: String,
    val profileImage: String,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,
) : Serializable {
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

data class FileRequest(
    val image: MultipartFile? = null,
    val thumbnail: MultipartFile? = null,
    val userId: Long,
    val name: String,
    val content: String? = null,
    val profileImage: String,
    val channelId: String,
    val fileType: DirectMessageType,
) {
    fun toDomain(userId: String, type: DirectMessageType): DirectMessage {
        return DirectMessage(
            userId = userId,
            name = name,
            profileImage = profileImage,
            channelId = channelId,
            type = type
        )
    }
}
