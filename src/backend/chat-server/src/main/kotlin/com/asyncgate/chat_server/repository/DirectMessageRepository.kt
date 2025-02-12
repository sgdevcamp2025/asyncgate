package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import com.asyncgate.chat_server.support.utility.toDomain
import com.asyncgate.chat_server.support.utility.toEntity
import org.springframework.stereotype.Repository

interface DirectMessageRepository {
    fun save(directMessage: DirectMessage): DirectMessage
    fun findById(id: String): DirectMessage?
}

@Repository
class DirectMessageRepositoryImpl(
    private val directMessageMongoRepository: DirectMessageMongoRepository,
) : DirectMessageRepository {

    override fun save(directMessage: DirectMessage): DirectMessage {
        return directMessageMongoRepository.save(directMessage.toEntity()).toDomain()
    }

    override fun findById(id: String): DirectMessage {
        return directMessageMongoRepository.findActiveById(id)
            ?.toDomain()
            ?: throw ChatServerException(FailType.DIRECT_MESSAGE_NOT_FOUND)
    }
}
