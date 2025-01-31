package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.service.GuildService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guild")
public class GuildController {

    private final GuildService guildService;

    @PostMapping
    public SuccessResponse<GuildResponse> create(
            final @AuthenticationPrincipal String userId,
            final @ModelAttribute GuildRequest request
    ) {
        GuildResponse response = guildService.create(userId, request);
        return SuccessResponse.created(response);
    }

    @PatchMapping("/{guildId}")
    public SuccessResponse<GuildResponse> update(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @ModelAttribute GuildRequest request
    ) {
        GuildResponse response = guildService.update(userId, guildId, request);
        return SuccessResponse.ok(response);
    }

    @DeleteMapping("/{guildId}")
    public SuccessResponse<String> delete(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        guildService.delete(userId, guildId);
        return SuccessResponse.ok(String.format("Guild Id[%s] 삭제 완료되었습니다.", guildId));
    }
}
