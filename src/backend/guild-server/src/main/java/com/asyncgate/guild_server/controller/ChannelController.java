package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.ChannelCreateRequest;
import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;
import com.asyncgate.guild_server.service.ChannelService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/channel")
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public SuccessResponse<ChannelResponse> create(
            final @AuthenticationPrincipal String userId,
            final @RequestBody ChannelCreateRequest request
    ) {
        ChannelResponse response = channelService.create(userId, request);
        return SuccessResponse.created(response);
    }

    @DeleteMapping("/{guildId}/{categoryId}/{channelId}")
    public SuccessResponse<String> delete(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @PathVariable String categoryId,
            final @PathVariable String channelId
    ) {
        channelService.delete(userId, guildId, categoryId, channelId);
        return SuccessResponse.ok(
                String.format(
                        "Guild Id[%s]의 Category Id[%s]의 Channel Id[%s]가 삭제 완료되었습니다.",
                        guildId, categoryId, channelId
                )
        );
    }

    @PatchMapping("/{guildId}/{categoryId}/{channelId}")
    public SuccessResponse<ChannelResponse> update(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId,
            final @PathVariable String categoryId,
            final @PathVariable String channelId,
            final @RequestBody ChannelUpdateRequest request

            ) {
        ChannelResponse response = channelService.update(userId, guildId, categoryId, channelId, request);
        return SuccessResponse.ok(response);
    }
}
