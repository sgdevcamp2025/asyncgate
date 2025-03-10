package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.controller.docs.GuildControllerDocs;
import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildInfoResponse;
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
public class GuildController implements GuildControllerDocs {

    private final GuildService guildService;

    @Override
    @PostMapping
    public SuccessResponse<GuildResponse> create(
            final @AuthenticationPrincipal String userId,
            final @ModelAttribute GuildRequest request
    ) {
        GuildResponse response = guildService.create(userId, request);
        return SuccessResponse.created(response);
    }

    @Override
    @GetMapping
    public SuccessResponse<GuildResponses> getMyGuilds(
            final @AuthenticationPrincipal String userId
    ) {
        GuildResponses guildResponses = guildService.readMyGuilds(userId);
        return SuccessResponse.created(guildResponses);
    }

    @Override
    @GetMapping("/rand")
    public SuccessResponse<GuildResponses> getRand(
            final @AuthenticationPrincipal String userId,
            final @RequestParam(required = false, defaultValue = "10") int limit
    ) {
        GuildResponses guildResponses = guildService.readRand(userId, limit);
        return SuccessResponse.created(guildResponses);
    }

    @Override
    @GetMapping("/{guildId}")
    public SuccessResponse<GuildInfoResponse> readOne(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        GuildInfoResponse response = guildService.readOne(userId, guildId);
        return SuccessResponse.ok(response);
    }

    @Override
    @PatchMapping("/{guildId}")
    public SuccessResponse<GuildResponse> update(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @ModelAttribute GuildRequest request
    ) {
        GuildResponse response = guildService.update(userId, guildId, request);
        return SuccessResponse.ok(response);
    }

    @Override
    @DeleteMapping("/{guildId}")
    public SuccessResponse<String> delete(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        guildService.delete(userId, guildId);
        return SuccessResponse.ok(String.format("Guild Id[%s] 삭제 완료되었습니다.", guildId));
    }
}
