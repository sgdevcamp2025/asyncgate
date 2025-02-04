package com.asyncgate.chat_server.repository

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.entity.DirectMessageEntity

interface DirectMessageRepository {

    fun save(directMessageEntity: DirectMessageEntity): DirectMessage
}
