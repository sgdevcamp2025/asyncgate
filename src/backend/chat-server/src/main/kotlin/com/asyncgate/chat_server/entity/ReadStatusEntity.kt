package com.asyncgate.chat_server.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "read_status")
class ReadStatusEntity(
    @Id
    val id: String,
    val userId: String,
    val channelId: String,
    var lastReadMessageId: String?,
) : MongoBaseTimeEntity()
