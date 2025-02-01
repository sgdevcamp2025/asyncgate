package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.dto.request.LoginMemberRequest;
import com.asyncgate.user_server.dto.request.RegisterTemporaryMemberRequest;
import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequest;
import com.asyncgate.user_server.dto.response.CheckEmailDuplicateResponse;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.support.response.SuccessResponse;
import com.asyncgate.user_server.usecase.CheckEmailDuplicateUseCase;
import com.asyncgate.user_server.usecase.LoginMemberUsecase;
import com.asyncgate.user_server.usecase.RegisterTemporaryMemberUseCase;
import com.asyncgate.user_server.usecase.ValidateAuthenticationCodeUseCase;
import io.lettuce.core.dynamic.annotation.Param;
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
    private final CheckEmailDuplicateUseCase CheckEmailDuplicateUseCase;

    /**
     * 1.0 임시 회원가입
     */
    @PostMapping("/sign-up")
    public SuccessResponse<?> signUp(
            @RequestBody final RegisterTemporaryMemberRequest request
    ) {
        RegisterTemporaryMemberUseCase.execute(request);
        return SuccessResponse.created("인증번호가 발송되었습니다.");
    }

    /**
     * 1.1 로그인
     */
    @PostMapping("/sign-in")
    public SuccessResponse<DefaultJsonWebTokenResponse> signIn(
            @RequestBody final LoginMemberRequest request
    ) {
        return SuccessResponse.ok(
                LoginMemberUsecase.execute(request)
        );
    }

    /**
     * 1.2 인증번호 인증
     */
    @PostMapping("/validation/authentication-code")
    public SuccessResponse<?> validateAuthentication(
            @RequestBody final ValidateAuthenticationCodeRequest request
    ) {
        ValidateAuthenticationCodeUseCase.execute(request);
        return SuccessResponse.ok("인증되었습니다.");
    }

    /**
     * 1.3 이메일 중복 검사
     */
    @PostMapping("/validation/email")
    public SuccessResponse<CheckEmailDuplicateResponse> checkEmailDuplicate(
            @Param("email") final String email
    ) {
        return SuccessResponse.ok(
                CheckEmailDuplicateUseCase.execute(email)
        );
    }
}