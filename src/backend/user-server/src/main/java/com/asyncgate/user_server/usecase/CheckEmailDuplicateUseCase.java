package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.response.CheckEmailDuplicateResponse;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface CheckEmailDuplicateUseCase {
    /**
     * 이메일 중복 검사
     *
     * @param email (email)
     */
    CheckEmailDuplicateResponse execute(final String email);
}
