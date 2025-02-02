package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildInfoResponse;
import com.asyncgate.guild_server.dto.response.GuildRandResponses;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.dto.response.GuildResponses;
import com.asyncgate.guild_server.service.GuildService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guilds")
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

    @GetMapping
    public SuccessResponse<GuildResponses> getMyGuilds(
            final @AuthenticationPrincipal String userId,
            final @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        GuildResponses guildResponses = guildService.readMyGuilds(userId, limit);
        return SuccessResponse.created(guildResponses);
    }

    @GetMapping("/rand")
    public SuccessResponse<GuildRandResponses> getRand(
            final @AuthenticationPrincipal String userId,
            final @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        GuildRandResponses guildResponses = guildService.readRand(userId, limit);
        return SuccessResponse.created(guildResponses);
    }

    @GetMapping("/{guildId}")
    public SuccessResponse<GuildInfoResponse> raedOne(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        GuildInfoResponse response = guildService.readOne(userId, guildId);
        return SuccessResponse.ok(response);
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
