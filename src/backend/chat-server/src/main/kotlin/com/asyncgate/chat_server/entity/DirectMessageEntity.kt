package com.asyncgate.chat_server.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "directMessage")
class DirectMessageEntity(
    @Id
    val id: String,

    val channelId: String,
    val userId: String,
    val profileImage: String,

    val read: Boolean,

    val name: String,
    val content: String,
    val thumbnail: String? = null,

    val parentId: String? = null,
    val parentName: String? = null,
    val parentContent: String? = null,
) : MongoBaseTimeEntity()
