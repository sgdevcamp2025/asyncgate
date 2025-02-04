package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.entity.DirectMessageEntity
import com.asyncgate.chat_server.kafka.toDomain
import org.springframework.stereotype.Repository

@Repository
class DirectMessageRepositoryImpl(
    private val directMessageMongoRepository: DirectMessageMongoRepository,
) : DirectMessageRepository {

    override fun save(directMessageEntity: DirectMessageEntity): DirectMessage {
        return directMessageMongoRepository.save(directMessageEntity).toDomain()
    }
}
