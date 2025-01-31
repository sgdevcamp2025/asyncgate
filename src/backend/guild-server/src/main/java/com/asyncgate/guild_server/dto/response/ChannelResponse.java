package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.ChannelType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChannelResponse(
        String channelId, String guildId, String categoryId, String name,
        String topic, ChannelType channelType, boolean isPrivate
) {

    public static ChannelResponse create(
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

    public static ChannelResponse update(
            final String channelId, final String guildId, final String categoryId,
            final String name, final String topic, final ChannelType channelType, final boolean isPrivate
    ) {
        return ChannelResponse.builder()
                .channelId(channelId)
                .guildId(guildId)
                .categoryId(categoryId)
                .name(name)
                .topic(topic)
                .channelType(channelType)
                .isPrivate(isPrivate)
                .build();
    }
}
