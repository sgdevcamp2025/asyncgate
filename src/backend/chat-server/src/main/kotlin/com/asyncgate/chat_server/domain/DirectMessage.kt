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
) {
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
