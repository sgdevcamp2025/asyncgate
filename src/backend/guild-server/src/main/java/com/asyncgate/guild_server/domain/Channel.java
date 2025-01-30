package com.asyncgate.guild_server.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Channel implements Identifiable {
    private final String id;
    private final String guildId;
    private final String categoryId;
    private final String name;
    private final ChannelType channelType;
    private final boolean isPrivate;

    @Builder
    private Channel(String id, String guildId, String categoryId, String name,
                    ChannelType channelType, boolean isPrivate) {
        this.id = id;
        this.guildId = guildId;
        this.categoryId = categoryId;
        this.name = name;
        this.channelType = channelType;
        this.isPrivate = isPrivate;
    }

    public static Channel create(
            final String guildId, final String categoryId, final String name,
            final ChannelType channelType, final boolean isPrivate
    ) {
        String id = UUID.randomUUID().toString();
        return Channel.builder()
                .id(id)
                .guildId(guildId)
                .categoryId(categoryId)
                .name(name)
                .channelType(channelType)
                .isPrivate(isPrivate)
                .build();
    }
}
