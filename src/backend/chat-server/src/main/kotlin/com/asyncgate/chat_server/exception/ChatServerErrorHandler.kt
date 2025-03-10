package com.asyncgate.chat_server.exception

import com.asyncgate.chat_server.support.response.FailResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException

@ControllerAdvice
class ChatServerErrorHandler {
    private val log: Logger = LoggerFactory.getLogger(ChatServerErrorHandler::class.java)

    @ExceptionHandler(ChatServerException::class)
    fun handleApiException(e: ChatServerException): ResponseEntity<FailResponse> {
        val errorType: FailType = e.failType
        val response: FailResponse = FailResponse.of(
            errorType.errorCode,
            errorType.message,
            errorType.status.value()
        )
        return ResponseEntity.status(errorType.status).body(response)
    }

    @ExceptionHandler(NoResourceFoundException::class)
    fun handleResourceException(exception: NoResourceFoundException): ResponseEntity<FailResponse> {
        val errorType = FailType.RESOURCE_NOT_FOUND
        val response: FailResponse = FailResponse.of(
            errorType.errorCode,
            errorType.message,
            errorType.status.value()
        )
        return ResponseEntity.status(errorType.status).body(response)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingRequestParamException(e: MissingServletRequestParameterException): ResponseEntity<FailResponse> {
        val errorType = FailType.REQUEST_PARAMETER_MISSING
        val response: FailResponse = FailResponse.of(
            errorType.errorCode,
            errorType.message,
            errorType.status.value()
        )
        return ResponseEntity.status(errorType.status).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception) {
        log.error("🚨 [Global Error] ${exception.message}", exception)
    }
}
