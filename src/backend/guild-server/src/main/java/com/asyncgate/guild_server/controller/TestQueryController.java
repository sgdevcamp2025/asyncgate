package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
@RequiredArgsConstructor
public class TestQueryController {

    @GetMapping("/health")
    public SuccessResponse<String> health() {
        return SuccessResponse.ok("security no need");
    }
}
