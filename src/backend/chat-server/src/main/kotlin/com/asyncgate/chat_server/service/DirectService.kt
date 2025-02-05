package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.DirectMessage
import com.asyncgate.chat_server.domain.ReadStatus

interface DirectService {
    fun send(message: DirectMessage)
    fun updateReadStatus(readStatus: ReadStatus)
}
