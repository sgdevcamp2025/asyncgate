package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.entity.DirectMessageEntity
import org.springframework.data.mongodb.repository.MongoRepository

interface DirectMessageMongoRepository : MongoRepository<DirectMessageEntity, String>
