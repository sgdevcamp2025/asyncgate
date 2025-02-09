package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.dto.request.*;
import com.asyncgate.user_server.dto.response.CheckEmailDuplicateResponse;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.security.annotation.MemberID;
import com.asyncgate.user_server.support.response.SuccessResponse;
import com.asyncgate.user_server.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberCommandController {

    private final RegisterTemporaryMemberUseCase RegisterTemporaryMemberUseCase;
    private final LoginMemberUsecase LoginMemberUsecase;
    private final ValidateAuthenticationCodeUseCase ValidateAuthenticationCodeUseCase;
    private final CheckEmailDuplicateUseCase CheckEmailDuplicateUseCase;
    private final UpdateUserInfoUseCase UpdateUserInfoUseCase;
    private final DeleteUserUseCase DeleteUserUseCase;
    private final UpdateDeviceTokenUseCase UpdateDeviceTokenUseCase;

    // 사소한 주석 추가 테스트테스트용 용용용가리
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
            @RequestParam("email") final String email
    ) {
        return SuccessResponse.ok(
                CheckEmailDuplicateUseCase.execute(email)
        );
    }

    /**
     * 1.4 디바이스 토큰 업데이트
     */
    @PatchMapping("/device-token")
    public SuccessResponse<?> updateDeviceToken(
            @MemberID final String userId,
            @RequestBody final UpdateDeviceTokenRequest request
    ) {
        UpdateDeviceTokenUseCase.execute(userId, request);
        return SuccessResponse.ok("디바이스 토큰 업데이트 완료");
    }

    /**
     * 1.5 유저 정보 수정
     */
    @PatchMapping("/info")
    public SuccessResponse<?> updateUserInfo(
            @MemberID final String userId,
            @RequestPart(value = "name", required = false) final String name,
            @RequestPart(value = "nickname", required = false) final String nickname,
            @RequestPart(value = "profile_image", required = false) final MultipartFile profileImage
    ) {
        UpdateUserInfoUseCase.execute(userId, name, nickname, profileImage);
        return SuccessResponse.ok("유저 정보 수정 완료");
    }

    /**
     * 1.6 회원탈퇴
     */
    @DeleteMapping("/auth")
    public SuccessResponse<?> deleteUser(@MemberID final String userId) {
        DeleteUserUseCase.execute(userId);
        return SuccessResponse.ok("회원탈퇴 완료");
    }
}