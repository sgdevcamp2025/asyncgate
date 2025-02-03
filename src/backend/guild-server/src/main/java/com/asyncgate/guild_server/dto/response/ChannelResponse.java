package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.domain.ChannelType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ChannelResponse(
        String channelId, String guildId, String categoryId, String name,
        String topic, ChannelType channelType, boolean isPrivate
) {

    public static ChannelResponse from(final Channel channel) {
        return ChannelResponse.builder()
                .channelId(channel.getId())
                .guildId(channel.getGuildId())
                .categoryId(channel.getCategoryId())
                .name(channel.getName())
                .topic(channel.getTopic())
                .channelType(channel.getChannelType())
                .isPrivate(channel.isPrivate())
                .build();
    }
}
