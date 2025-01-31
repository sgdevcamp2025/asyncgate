package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.LoginMemberRequestDto;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponseDto;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface LoginMemberUsecase {
    /**
     * 로그인
     *
     * @param requestDto (email, password)
     */
    DefaultJsonWebTokenResponseDto execute(LoginMemberRequestDto requestDto);
}
