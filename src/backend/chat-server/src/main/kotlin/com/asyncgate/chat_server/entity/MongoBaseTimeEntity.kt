package com.asyncgate.chat_server.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class MongoBaseTimeEntity {

    @CreatedDate
    lateinit var createdAt: LocalDateTime
        private set

    @LastModifiedDate
    lateinit var updatedAt: LocalDateTime
        private set
}
