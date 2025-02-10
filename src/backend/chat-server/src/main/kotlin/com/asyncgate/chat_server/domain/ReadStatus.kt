package com.asyncgate.chat_server.domain

import com.asyncgate.chat_server.support.utility.UUIDGenerator
import java.time.LocalDateTime

data class ReadStatus(
    val id: String? = null,
    val userId: String,
    val channelId: String,
    var lastReadMessageId: String?,

    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
) {
    fun updateLastReadMessageId(messageId: String): ReadStatus {
        return copy(
            id = id,
            userId = userId,
            channelId = channelId,
            lastReadMessageId = messageId
        )
    }

    companion object {
        fun create(
            userId: String,
            channelId: String,
        ): ReadStatus {
            return ReadStatus(
                id = UUIDGenerator.generate(),
                userId = userId,
                channelId = channelId,
                lastReadMessageId = null
            )
        }
    }
}
