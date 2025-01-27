package com.asyncgate.chat_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ChatServerApplication

fun main(args: Array<String>) {
    runApplication<ChatServerApplication>(*args)
}
