package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequest;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface ValidateAuthenticationCodeUseCase {
    /**
     * 인증번호 인증
     *
     * @param request (email, authenticationCode)
     */
    void execute(final ValidateAuthenticationCodeRequest request);
}
