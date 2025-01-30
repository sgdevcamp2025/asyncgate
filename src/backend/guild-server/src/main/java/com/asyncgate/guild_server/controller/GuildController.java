package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.response.GuildCreateResponse;
import com.asyncgate.guild_server.service.GuildService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/guild")
@RequiredArgsConstructor
public class GuildController {

    private final GuildService guildService;

    @PostMapping
    public SuccessResponse<GuildCreateResponse> createGuild(final @RequestBody GuildCreateRequest request) {
        GuildCreateResponse response = guildService.create(request);
        return SuccessResponse.created(response);
    }
}
