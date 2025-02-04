package com.asyncgate.chat_server.support.utility

object UUIDGenerator {
    fun generate(): String {
        return java.util.UUID.randomUUID().toString()
    }
}
