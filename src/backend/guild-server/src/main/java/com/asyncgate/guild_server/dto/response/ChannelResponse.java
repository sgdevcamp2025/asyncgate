package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.ChannelType;
import lombok.Builder;

public class ChannelResponse {
    private final String channelId;
    private final String guildId;
    private final String categoryId;
    private final String name;
    private final ChannelType channelType;
    private final boolean isPrivate;

    @Builder
    public ChannelResponse(
            String channelId, String guildId, String categoryId,
            String name, ChannelType channelType, boolean isPrivate
    ) {
        this.channelId = channelId;
        this.guildId = guildId;
        this.categoryId = categoryId;
        this.name = name;
        this.channelType = channelType;
        this.isPrivate = isPrivate;
    }

    public static ChannelResponse of(
            final String channelId, final String guildId, final String categoryId,
            final String name, final ChannelType channelType, final boolean isPrivate
    ) {
        return ChannelResponse.builder()
                .channelId(channelId)
                .guildId(guildId)
                .categoryId(categoryId)
                .name(name)
                .channelType(channelType)
                .isPrivate(isPrivate)
                .build();
    }
}
