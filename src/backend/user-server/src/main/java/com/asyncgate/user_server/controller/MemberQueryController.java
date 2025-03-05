package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.dto.response.ReadUserRoomProfileResponse;
import com.asyncgate.user_server.support.response.SuccessResponse;
import com.asyncgate.user_server.usecase.ReadUserRoomProfileUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberQueryController {

    private final ReadUserRoomProfileUseCase readUserRoomProfileUseCase;

    /**
     * 0.0 security no need 테스트용 get method
     */
    @GetMapping("/health")
    public SuccessResponse<String> health() {
        return SuccessResponse.ok("security no need");
    }

    /**
     * 1.7 회원 화상 간단 정보 조회
     */
    // test
    @GetMapping("/room/profile")
    public SuccessResponse<ReadUserRoomProfileResponse> readUserRoomProfile(@RequestParam("userId") final String userId) {
        return SuccessResponse.ok(readUserRoomProfileUseCase.execute(userId));
    }
}
