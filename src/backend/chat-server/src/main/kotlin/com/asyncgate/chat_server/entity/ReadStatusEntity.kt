package com.asyncgate.chat_server.entity

import com.asyncgate.chat_server.support.utility.IdGenerator
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "read_status")
class ReadStatusEntity(
    @get:Id
    val id: String = IdGenerator.generate(),
    val userId: String,
    val channelId: String,
    var lastReadMessageId: String?,
    var isDeleted: Boolean = false,
) : MongoBaseTimeEntity()
