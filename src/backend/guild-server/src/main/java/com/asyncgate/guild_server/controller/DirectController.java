package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.DirectChannelCreateRequest;
import com.asyncgate.guild_server.dto.response.DirectResponse;
import com.asyncgate.guild_server.dto.response.DirectResponses;
import com.asyncgate.guild_server.service.DirectService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/direct")
public class DirectController {

    private final DirectService directService;

    @PostMapping
    public SuccessResponse<DirectResponse> create(
            final @AuthenticationPrincipal String currentUserId,
            final @RequestBody DirectChannelCreateRequest request
    ) {
        return SuccessResponse.created(
                directService.create(currentUserId, request)
        );
    }

    @GetMapping
    public SuccessResponse<DirectResponses> get(
            final @AuthenticationPrincipal String currentUserId
    ) {
        return SuccessResponse.ok(
                directService.getDirectList(currentUserId)
        );
    }

    // 서버 내부 호출 전용
    @Hidden
    @GetMapping("/open/{direct-id}")
    public List<String> getDirectMemberIds(
            final @PathVariable("direct-id") String directId
    ) {
        return directService.getDirectDetail(directId);
    }

}
