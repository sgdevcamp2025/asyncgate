package com.asyncgate.guild_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildCreateResponse {

    private String id;
    private String name;
    private boolean isPrivate;

    public static GuildCreateResponse of(final String id, final String name, final boolean isPrivate) {
        return new GuildCreateResponse(id, name, isPrivate);
    }
}
