package com.asyncgate.guild_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildResponse {

    private String id;
    private String name;
    private boolean isPrivate;

    public static GuildResponse of(final String id, final String name, final boolean isPrivate) {
        return new GuildResponse(id, name, isPrivate);
    }
}
