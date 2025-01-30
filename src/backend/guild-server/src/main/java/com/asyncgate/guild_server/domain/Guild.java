package com.asyncgate.guild_server.domain;

import java.util.UUID;
import lombok.Getter;

@Getter
public class Guild implements Identifiable {

    private final String id;
    private final String name;
    private final boolean isPrivate;

    public Guild(final String id, final String name, final boolean isPrivate) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
    }

    public static Guild create(final String name, final boolean isPrivate) {
        String id = UUID.randomUUID().toString();
        return new Guild(id, name, isPrivate);
    }

    public Guild update(final String name, final boolean isPrivate) {
        return new Guild(this.id, name, isPrivate);
    }
}
