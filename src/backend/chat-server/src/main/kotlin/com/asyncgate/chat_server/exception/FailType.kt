package com.asyncgate.chat_server.exception

import org.springframework.http.HttpStatus

enum class FailType(
    val status: HttpStatus,
    val errorCode: String,
    val message: String,
) {
    // 예시
    // 400대  JWT_BAD_REQUEST  500 대 _JWT_BAD_REQUEST
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Jwt_4001", "잘못된 JWT Token 타입입니다."),

    // 알 수 없는 에러
    _UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server_5000", "알 수 없는 에러가 발생하였습니다."),

    // S3
    _UPLOAD_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5001", "S3 이미지 업로드에 실패하였습니다."),
    _DELETE_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5002", "S3 이미지 제거에 실패하였습니다."),
    _FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "S3_5003", "파일이 S3에 존재하지 않습니다.");

}
