package com.asyncgate.user_server.security.exception;

// 각 application에 맞는 failType으로 정의해주세요 !
import com.asyncgate.user_server.exception.FailType;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException {

    private final FailType failType;

    public CommonException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}