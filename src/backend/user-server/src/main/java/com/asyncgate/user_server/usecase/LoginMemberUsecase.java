package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.LoginMemberRequest;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface LoginMemberUsecase {
    /**
     * 로그인
     *
     * @param request (email, password)
     */
    DefaultJsonWebTokenResponse execute(final LoginMemberRequest request);
}
