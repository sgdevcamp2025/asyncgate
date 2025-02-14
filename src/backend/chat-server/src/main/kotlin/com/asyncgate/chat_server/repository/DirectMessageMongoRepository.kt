package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.entity.DirectMessageEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface DirectMessageMongoRepository : MongoRepository<DirectMessageEntity, String> {
    @Query("{ '_id': ?0, 'isDeleted': false }")
    fun findActiveById(id: String): DirectMessageEntity?

    @Query("{ 'channelId': ?0, 'isDeleted': false }")
    fun findByChannelId(channelId: String, pageable: Pageable): Page<DirectMessageEntity>
}
