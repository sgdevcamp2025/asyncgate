package com.asyncgate.user_server.controller;

import com.asyncgate.user_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestQueryController {

    /**
     * 0.0 security no need 테스트용 get method
     */
    @GetMapping("/connection-test")
    public SuccessResponse<String> testGet() {
        SuccessResponse<String> response = SuccessResponse.created("security no need");
        return response;
    }
}
