package com.asyncgate.chat_server.exception

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class WebSocketErrorHandler {
    private val log: Logger = LoggerFactory.getLogger(WebSocketErrorHandler::class.java)

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception) {
        log.error("🚨 [WebSocket Error] ${exception.message}", exception)
    }
}
