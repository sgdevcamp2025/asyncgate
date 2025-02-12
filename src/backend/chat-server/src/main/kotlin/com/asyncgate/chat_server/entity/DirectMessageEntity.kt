package com.asyncgate.chat_server.entity

import com.asyncgate.chat_server.domain.DirectMessageType
import com.asyncgate.chat_server.support.utility.IdGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "directMessage")
data class DirectMessageEntity(
    @get:Id
    val id: String = IdGenerator.generate(),

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
    var isDeleted: Boolean = false,
) : MongoBaseTimeEntity()
