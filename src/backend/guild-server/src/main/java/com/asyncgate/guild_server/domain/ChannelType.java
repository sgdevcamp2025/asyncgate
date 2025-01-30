package com.asyncgate.guild_server.domain;

import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import java.util.Arrays;

public enum ChannelType {
    VOICE,
    TEXT,

    ;

    public static ChannelType from(final String value) {
        return Arrays.stream(ChannelType.values())
                .filter(type -> type.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new GuildServerException(FailType.CHANNEL_BAD_REQUEST));
    }
}
