package com.asyncgate.signaling_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailType {

    // 예시
    // 400대  JWT_BAD_REQUEST  500 대 _JWT_BAD_REQUEST
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Jwt_4001", "잘못된 JWT Token 타입입니다."),

    // 알 수 없는 에러
    _UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server_5000", "알 수 없는 에러가 발생하였습니다."),
    _CONCURRENT_UPDATE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server_5001", "동시 업데이트 에러가 발생하였습니다."),

    // S3
    _UPLOAD_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5001", "S3 이미지 업로드에 실패하였습니다."),
    _DELETE_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5002", "S3 이미지 제거에 실패하였습니다."),
    _FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "S3_5003", "파일이 S3에 존재하지 않습니다."),

    // Unauthorized Error
    INVALID_HEADER_ERROR(HttpStatus.UNAUTHORIZED, "Member_40108", "헤더가 올바르지 않습니다."),

    // Access Denied Error
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access_40300", "접근 권한이 없습니다."),
    NOT_LOGIN_USER(HttpStatus.FORBIDDEN, "Access_40301", "로그인하지 않은 사용자입니다."),

    // 채팅룸이 존재하지 않음
    _ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "Room_4001", "채팅룸이 존재하지 않습니다."),

    // member가 존재하지 않음
    _MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member_4001", "멤버가 존재하지 않습니다.");

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    FailType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
