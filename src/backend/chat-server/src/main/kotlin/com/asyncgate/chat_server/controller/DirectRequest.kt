package com.asyncgate.chat_server.controller

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.DirectMessageType
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile
import java.io.Serializable

@Schema(description = "Direct 메시지 생성 요청 DTO")
@JsonSerialize
@JsonDeserialize
data class DirectMessageCreate(
    @Schema(description = "채널 ID, 다이렉트 채팅방 생성 후 id. DirectId와 같음", example = "channel-12345")
    val channelId: String,

    @Schema(description = "프로필 이미지 URL", example = "http://example.com/image.png")
    val profileImage: String,

    @Schema(description = "사용자 이름", example = "John Doe")
    val name: String,

    @Schema(description = "메시지 내용", example = "Hello!")
    val content: String,

    @Schema(description = "썸네일 URL", example = "http://example.com/thumbnail.png", nullable = true)
    val thumbnail: String? = null,

    @Schema(description = "부모 메시지 ID", example = "msg-12345", nullable = true)
    val parentId: String? = null,

    @Schema(description = "부모 메시지 작성자 이름", example = "Jane Doe", nullable = true)
    val parentName: String? = null,

    @Schema(description = "부모 메시지 내용", example = "Previous message content", nullable = true)
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

@Schema(description = "Direct 메시지 수정 요청 DTO")
@JsonSerialize
@JsonDeserialize
data class DirectMessageEditRequest(
    @Schema(description = "메시지 ID", example = "msg-12345")
    val id: String,

    @Schema(description = "채널 ID", example = "channel-12345")
    val channelId: String,

    @Schema(description = "사용자 이름", example = "John Doe")
    val name: String,

    @Schema(description = "메시지 내용", example = "Hello, edited!")
    val content: String,
) : Serializable {
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

@Schema(description = "Direct 메시지 삭제 요청 DTO")
@JsonSerialize
@JsonDeserialize
data class DirectMessageDeleteRequest(
    @Schema(description = "채널 ID", example = "channel-12345")
    val channelId: String,

    @Schema(description = "메시지 ID", example = "msg-12345")
    val id: String,
) : Serializable {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            id = id,
            channelId = channelId,
            userId = userId,
            type = DirectMessageType.DELETE
        )
    }
}

@Schema(description = "Direct 메시지 타이핑 요청 DTO")
@JsonSerialize
@JsonDeserialize
data class DirectMessageTypingRequest(
    @Schema(description = "채널 ID", example = "channel-12345")
    val channelId: String,

    @Schema(description = "타이핑 중 메시지 내용", example = "User is typing...")
    val content: String,

    @Schema(description = "사용자 이름", example = "John Doe")
    val name: String,
) : Serializable {
    fun toDomain(userId: String): DirectMessage {
        return DirectMessage(
            channelId = channelId,
            userId = userId,
            name = name,
            type = DirectMessageType.TYPING
        )
    }
}

@Schema(description = "파일 업로드 요청 DTO")
@JsonSerialize
@JsonDeserialize
data class FileRequest(
    @Schema(description = "업로드 이미지 파일", nullable = true)
    val image: MultipartFile? = null,

    @Schema(description = "썸네일 이미지 파일", nullable = true)
    val thumbnail: MultipartFile? = null,

    @Schema(description = "사용자 ID", example = "asdfbnmzxcvasdf1234")
    val userId: String,

    @Schema(description = "사용자 이름", example = "John Doe")
    val name: String,

    @Schema(description = "메시지 내용", example = "파일 메시지", nullable = true)
    val content: String? = null,

    @Schema(description = "프로필 이미지 URL", example = "http://example.com/profile.png")
    val profileImage: String,

    @Schema(description = "채널 ID", example = "channel-12345")
    val channelId: String,

    @Schema(description = "메시지 타입", example = "FILE")
    val fileType: DirectMessageType,
) : Serializable {
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
