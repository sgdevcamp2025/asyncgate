package com.asyncgate.chat_server.support.utility

import org.bson.types.ObjectId

object IdGenerator {
    fun generate(): String {
        return ObjectId().toHexString()
    }
}
