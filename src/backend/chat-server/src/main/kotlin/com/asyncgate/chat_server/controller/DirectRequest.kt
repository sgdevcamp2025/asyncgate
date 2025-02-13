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
    val type: String? = null,
    val image: MultipartFile,
    val thumbnail: MultipartFile,
    val userId: Long,
    val name: String,
    val profileImage: String,
    val guildId: Long? = null,
    val channelId: Long,
    val fileType: FileType,
)

enum class FileType {
    IMAGE, // 이미지 파일 (JPG, PNG, GIF, WEBP 등)
    VIDEO, // 비디오 파일 (MP4, MOV, AVI 등)
    AUDIO, // 오디오 파일 (MP3, WAV, OGG 등)
    DOCUMENT, // 문서 파일 (PDF, DOCX, TXT 등)
    ARCHIVE, // 압축 파일 (ZIP, RAR 등)
    CODE, // 코드 파일 (JS, PY, JAVA, C, C++ 등)
    SNIPPET, // 짧은 코드 스니펫
    GIF, // GIF 파일 (애니메이션 이미지)
    STICKER, // 디스코드의 스티커 파일
    EMOJI, // 커스텀 이모지 파일
    OTHER, // 범용적인 기타 파일
}
