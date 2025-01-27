package com.asyncgate.state_server.support.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

@JsonPropertyOrder("httpStatus", "message", "time", "result")
data class SuccessResponse<T>(
    @JsonProperty("httpStatus")
    val httpStatus: Int,
    val message: String,
    val time: LocalDateTime = LocalDateTime.now(),
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val result: T? = null,
) {
    companion object {
        fun <T> ok(result: T): SuccessResponse<T> {
            return SuccessResponse(
                httpStatus = SuccessResponseStatus.SUCCESS.httpStatusCode(),
                message = SuccessResponseStatus.SUCCESS.message,
                result = result
            )
        }

        fun <T> created(result: T): SuccessResponse<T> {
            return SuccessResponse(
                httpStatus = SuccessResponseStatus.SUCCESS.httpStatusCode(),
                message = SuccessResponseStatus.CREATED.message,
                result = result
            )
        }

        fun <T> accepted(result: T): SuccessResponse<T> {
            return SuccessResponse(
                httpStatus = SuccessResponseStatus.SUCCESS.httpStatusCode(),
                message = SuccessResponseStatus.ACCEPTED.message,
                result = result
            )
        }
    }
}
