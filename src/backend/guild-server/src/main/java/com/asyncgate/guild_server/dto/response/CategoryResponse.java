package com.asyncgate.guild_server.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CategoryResponse {

    private final String categoryId;
    private final String name;
    private final boolean isPrivate;
    private final String guildId;

    private CategoryResponse(final String categoryId, final String name, final boolean isPrivate, final String guildId) {
        this.categoryId = categoryId;
        this.name = name;
        this.isPrivate = isPrivate;
        this.guildId = guildId;
    }

    public static CategoryResponse of(final String categoryId, final String name, final boolean isPrivate, final String guildId) {
        return new CategoryResponse(categoryId, name, isPrivate, guildId);
    }
}
