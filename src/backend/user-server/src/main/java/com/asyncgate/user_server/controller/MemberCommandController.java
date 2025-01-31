package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.dto.request.LoginMemberRequestDto;
import com.asyncgate.user_server.dto.request.RegisterTemporaryMemberRequestDto;
import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequestDto;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponseDto;
import com.asyncgate.user_server.support.response.SuccessResponse;
import com.asyncgate.user_server.usecase.LoginMemberUsecase;
import com.asyncgate.user_server.usecase.RegisterTemporaryMemberUseCase;
import com.asyncgate.user_server.usecase.ValidateAuthenticationCodeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberCommandController {

    private final RegisterTemporaryMemberUseCase RegisterTemporaryMemberUseCase;
    private final LoginMemberUsecase LoginMemberUsecase;
    private final ValidateAuthenticationCodeUseCase ValidateAuthenticationCodeUseCase;

    /**
     * 1.0 임시 회원가입
     */
    @PostMapping("/sign-up")
    public SuccessResponse<?> signUp(
            @RequestBody RegisterTemporaryMemberRequestDto requestDto
    ) {
        RegisterTemporaryMemberUseCase.execute(requestDto);
        return SuccessResponse.created(null);
    }

    /**
     * 1.1 로그인
     */
    @PostMapping("/sign-in")
    public SuccessResponse<DefaultJsonWebTokenResponseDto> signIn(
            @RequestBody LoginMemberRequestDto requestDto
    ) {
        return SuccessResponse.ok(
                LoginMemberUsecase.execute(requestDto)
        );
    }

    /**
     * 1.2 인증번호 인증
     */
    @PostMapping("/authentication-code")
    public SuccessResponse<?> validateAuthentication(
            @RequestBody ValidateAuthenticationCodeRequestDto requestDto
    ) {
        ValidateAuthenticationCodeUseCase.execute(requestDto);
        return SuccessResponse.ok(null);
    }
}