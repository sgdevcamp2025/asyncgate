package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequestDto;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface ValidateAuthenticationCodeUseCase {
    /**
     * 인증번호 인증
     *
     * @param requestDto (email, authenticationCode)
     */
    void execute(ValidateAuthenticationCodeRequestDto requestDto);
}
