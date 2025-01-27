package com.asyncgate.state_server.exception

import org.springframework.http.HttpStatus

enum class FailType(
    val status: HttpStatus,
    val errorCode: String,
    val message: String,
) {
    // 예시
    // 400대  JWT_BAD_REQUEST  500 대 _JWT_BAD_REQUEST
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Jwt_4001", "잘못된 JWT Token 타입입니다."),
}
