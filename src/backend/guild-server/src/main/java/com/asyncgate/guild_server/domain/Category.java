package com.asyncgate.guild_server.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Category implements Identifiable {
    private final String id;
    private final String name;
    private final boolean isPrivate;
    private final String guildId;

    @Builder
    private Category(String id, String name, boolean isPrivate, String guildId) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
        this.guildId = guildId;
    }

    public static Category create(final String name, final String guildId, final boolean isPrivate) {
        String id = UUID.randomUUID().toString();
        return new Category(id, name, isPrivate, guildId);
    }
}
