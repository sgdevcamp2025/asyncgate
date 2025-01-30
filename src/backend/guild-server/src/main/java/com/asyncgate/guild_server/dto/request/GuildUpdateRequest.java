package com.asyncgate.guild_server.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GuildUpdateRequest {

    private String name;
    private boolean isPrivate;
}
