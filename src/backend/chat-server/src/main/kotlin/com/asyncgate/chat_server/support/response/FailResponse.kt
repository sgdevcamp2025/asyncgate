package com.asyncgate.chat_server.support.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("httpStatus", "errorCode", "message", "time")
data class FailResponse(
    @JsonProperty("httpStatus")
    val httpStatus: Int,
    val errorCode: String,
    val message: String,
    val time: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun of(
            errorCode: String,
            message: String,
            httpStatus: Int,
        ): FailResponse {
            return FailResponse(
                httpStatus = httpStatus,
                errorCode = errorCode,
                message = message
            )
        }
    }
}
