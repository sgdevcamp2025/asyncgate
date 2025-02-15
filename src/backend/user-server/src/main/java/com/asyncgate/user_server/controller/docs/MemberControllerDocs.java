package com.asyncgate.user_server.controller.docs;

import com.asyncgate.user_server.dto.request.LoginMemberRequest;
import com.asyncgate.user_server.dto.request.RegisterTemporaryMemberRequest;
import com.asyncgate.user_server.dto.request.UpdateDeviceTokenRequest;
import com.asyncgate.user_server.dto.request.ValidateAuthenticationCodeRequest;
import com.asyncgate.user_server.dto.response.CheckEmailDuplicateResponse;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.security.annotation.MemberID;
import com.asyncgate.user_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User", description = "유저 관련 API")
public interface MemberControllerDocs {

    @Operation(summary = "임시 회원가입", description = "임시 회원가입을 진행하고 인증번호를 발송합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "인증번호가 발송되었습니다.")
    })
    @PostMapping("/sign-up")
    SuccessResponse<?> signUp(@RequestBody RegisterTemporaryMemberRequest request);

    @Operation(summary = "로그인", description = "유저가 로그인을 시도합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공")
    })
    @PostMapping("/sign-in")
    SuccessResponse<DefaultJsonWebTokenResponse> signIn(@RequestBody LoginMemberRequest request);

    @Operation(summary = "인증번호 인증", description = "사용자가 인증번호를 입력하여 인증을 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "인증 성공")
    })
    @PostMapping("/validation/authentication-code")
    SuccessResponse<?> validateAuthentication(@RequestBody ValidateAuthenticationCodeRequest request);

    @Operation(summary = "이메일 중복 검사", description = "이메일이 중복되었는지 검사합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "검사 성공")
    })
    @PostMapping("/validation/email")
    SuccessResponse<CheckEmailDuplicateResponse> checkEmailDuplicate(@RequestParam("email") String email);

    @Operation(summary = "디바이스 토큰 업데이트", description = "유저의 디바이스 토큰을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "디바이스 토큰 업데이트 완료")
    })
    @PatchMapping("/device-token")
    SuccessResponse<?> updateDeviceToken(@MemberID String userId, @RequestBody UpdateDeviceTokenRequest request);

    @Operation(summary = "유저 정보 수정", description = "유저 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 수정 완료")
    })
    @PatchMapping(value = "/info", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    SuccessResponse<?> updateUserInfo(
            @MemberID String userId,
            @RequestPart(value = "name", required = false) String name,
            @RequestPart(value = "nickname", required = false) String nickname,
            @RequestPart(value = "profile_image", required = false) MultipartFile profileImage
    );

    @Operation(summary = "회원 탈퇴", description = "유저가 회원탈퇴를 진행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴 완료")
    })
    @DeleteMapping("/auth")
    SuccessResponse<?> deleteUser(@MemberID String userId);
}
