package com.asyncgate.chat_server.support.response

import org.springframework.http.HttpStatus

enum class SuccessResponseStatus(
    private val httpStatus: HttpStatus,
    val message: String,
) {
    SUCCESS(HttpStatus.OK, "요청에 성공하였습니다."),
    CREATED(HttpStatus.CREATED, "정상적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "요청이 접수되었으며, 백그라운드에서 처리가 진행 중입니다."),
    ;

    fun httpStatusCode(): Int {
        return httpStatus.value()
    }
}
