package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.request.GuildUpdateRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.service.GuildService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/guild")
@RequiredArgsConstructor
public class GuildController {

    private final GuildService guildService;

    @PostMapping
    public SuccessResponse<GuildResponse> create(
            final @AuthenticationPrincipal String userId,
            final @RequestBody GuildCreateRequest request
    ) {
        GuildResponse response = guildService.create(userId, request);
        return SuccessResponse.created(response);
    }

    @PatchMapping("/{guildId}")
    public SuccessResponse<GuildResponse> update(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @RequestBody GuildUpdateRequest request
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
