package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.domain.ReadStatus
import com.asyncgate.chat_server.support.utility.toDomain
import com.asyncgate.chat_server.support.utility.toEntity
import org.springframework.stereotype.Repository

interface ReadStatusRepository {
    fun save(readStatus: ReadStatus): ReadStatus
    fun findByUserIdAndChannelId(userId: String, channelId: String): ReadStatus?
}

@Repository
class ReadStatusRepositoryImpl(
    private val mongoRepository: ReadStatusMongoRepository,
) : ReadStatusRepository {

    override fun save(readStatus: ReadStatus): ReadStatus {
        return mongoRepository.save(readStatus.toEntity()).toDomain()
    }

    override fun findByUserIdAndChannelId(userId: String, channelId: String): ReadStatus? {
        return mongoRepository.findByUserIdAndChannelId(userId, channelId)?.toDomain()
    }
}
