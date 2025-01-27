package com.asyncgate.chat_server.exception

class ChatServerException(
    val failType: FailType,
) : RuntimeException(failType.message)
