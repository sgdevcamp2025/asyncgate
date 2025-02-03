package com.asyncgate.guild_server.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
public class Guild implements Identifiable {
    private final String id;
    private String name;
    private boolean isPrivate;
    private String profileImageUrl;

    @Builder
    private Guild(String id, String name, boolean isPrivate, String profileImageUrl) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
        this.profileImageUrl = profileImageUrl;
    }

    public static Guild create(final String name, final boolean isPrivate, final String profileImageUrl) {
        String id = UUID.randomUUID().toString();
        return new Guild(id, name, isPrivate, profileImageUrl);
    }

    public void update(final String name, final boolean isPrivate, final String profileImageUrl) {
        if (StringUtils.hasText(name)) {
            this.name = name;
        }
        this.isPrivate = isPrivate;
        this.profileImageUrl = profileImageUrl;
    }

}
