package com.asyncgate.chat_server.entity

import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

abstract class MongoBaseTimeEntity {

    @LastModifiedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
}
