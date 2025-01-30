package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.dto.request.ChannelRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;
import com.asyncgate.guild_server.service.ChannelService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping
    public SuccessResponse<ChannelResponse> create(
            final @AuthenticationPrincipal String userId,
            final @RequestBody ChannelRequest request
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
}
