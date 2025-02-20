package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.entity.ReadStatusEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface ReadStatusMongoRepository : MongoRepository<ReadStatusEntity, String> {
    fun findByUserIdAndChannelId(userId: String, channelId: String): ReadStatusEntity?
}
