package com.asyncgate.guild_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FailType {

    // 예시
    // 400대  JWT_BAD_REQUEST  500 대 _JWT_BAD_REQUEST
    JWT_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Jwt_4001", "잘못된 JWT Token 타입입니다."),
    _JWT_INVALID_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "Jwt5001", "Api GateWay에서 전달된 토큰이 잘못된 토큰입니다."),

    // 알 수 없는 에러
    _UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server_5000", "알 수 없는 에러가 발생하였습니다."),

    // S3
    _UPLOAD_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5001", "S3 이미지 업로드에 실패하였습니다."),
    _DELETE_FILE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S3_5002", "S3 이미지 제거에 실패하였습니다."),
    _FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "S3_5003", "파일이 S3에 존재하지 않습니다."),

    // Guild
    GUILD_NOT_FOUND(HttpStatus.NOT_FOUND, "Guild_4041", "Guild를 찾을 수 없습니다."),
    GUILD_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Guild_4042", "Guild에 존재하는 회원이 아닙니다."),
    GUILD_PERMISSION_DENIED(HttpStatus.FORBIDDEN, "Guild_4031", "Guild를 삭제하거나 수정할 권한이 없습니다."),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "Category_4041", "Category를 찾을 수 없습니다."),

    // Channel
    CHANNEL_BAD_REQUEST(HttpStatus.BAD_REQUEST, "Channel_4001", "채널 타입은 VOICE 또는 TEXT만 가능합니다."),
    CHANNEL_NOT_FOUND(HttpStatus.NOT_FOUND, "Channel_4041", "채널을 찾을 수 없습니다."),

    // Guild Invitation
    INVITATION_NOT_FOUND(HttpStatus.NOT_FOUND, "Invitation_4041", "해당 초대를 찾을 수 없습니다."),
    INVITATION_ALREADY_ACCEPTED(HttpStatus.BAD_REQUEST, "Invitation_4001", "이미 수락된 초대입니다."),
    INVITATION_ALREADY_REJECTED(HttpStatus.BAD_REQUEST, "Invitation_4002", "이미 거절된 초대입니다."),
    INVITATION_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "Invitation_4003", "이미 취소된 초대입니다."),
    INVITATION_ALREADY_EXPIRED(HttpStatus.BAD_REQUEST, "Invitation_4004", "이미 만료된 초대입니다."),
    INVITATION_INVALID_STATUS(HttpStatus.BAD_REQUEST, "Invitation_4005", "잘못된 초대 상태입니다."),

    ;

    private final HttpStatus status;
    private final String errorCode;
    private final String message;

    FailType(final HttpStatus status, final String errorCode, final String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
