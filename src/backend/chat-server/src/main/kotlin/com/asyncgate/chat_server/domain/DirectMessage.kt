package com.asyncgate.chat_server.domain

import com.asyncgate.chat_server.support.utility.IdGenerator
import java.time.LocalDateTime

class DirectMessage(
    val id: String,
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
    var updatedAt: LocalDateTime? = null,
    var isDeleted: Boolean = false,
) {
    companion object {
        fun create(
            channelId: String,
            userId: String,
            type: DirectMessageType,
            profileImage: String? = null,
            name: String? = null,
            content: String? = null,
            thumbnail: String? = null,
            parentId: String? = null,
            parentName: String? = null,
            parentContent: String? = null,
        ): DirectMessage {
            return DirectMessage(
                id = IdGenerator.generate(),
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
                isDeleted = false
            )
        }
    }

    /**
     * 현재 메시지를 "삭제" 상태로 변경한 새로운 도메인 객체 반환.
     * (실제 삭제 플래그는 엔티티 변환 시 toEntity()에서 처리)
     */
    fun markDeleted(): DirectMessage {
        return DirectMessage(
            id = this.id,
            channelId = this.channelId,
            userId = this.userId,
            type = this.type,
            profileImage = this.profileImage,
            read = this.read,
            name = this.name,
            content = this.content,
            thumbnail = this.thumbnail,
            parentId = this.parentId,
            parentName = this.parentName,
            parentContent = this.parentContent,
            isDeleted = true
        )
    }

    /**
     * 현재 메시지를 수정하여, 새로운 메시지(수정본)를 생성.
     * 새로운 메시지 type은 EDIT로 변경됨.
     */
    fun withEdit(newName: String, newContent: String): DirectMessage {
        return DirectMessage(
            id = this.id,
            channelId = this.channelId,
            userId = this.userId,
            type = DirectMessageType.EDIT,
            profileImage = this.profileImage,
            read = this.read,
            name = newName,
            content = newContent,
            thumbnail = this.thumbnail,
            parentId = this.parentId,
            parentName = this.parentName,
            parentContent = this.parentContent,
            isDeleted = false
        )
    }

    override fun toString(): String {
        return "DirectMessage(" +
            "id=$id, " +
            "channelId='$channelId', " +
            "userId='$userId', " +
            "type=$type, " +
            "profileImage=$profileImage, " +
            "read=$read, " +
            "name=$name, " +
            "content=$content, " +
            "thumbnail=$thumbnail, " +
            "parentId=$parentId, " +
            "parentName=$parentName, " +
            "parentContent=$parentContent, " +
            "createdAt=$createdAt, " +
            "updatedAt=$updatedAt" +
            ")"
    }
}

enum class DirectMessageType {
    CREATE,
    EDIT,
    DELETE,
    TYPING,

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
