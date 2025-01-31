package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Category;

public record CategoryResponse(String categoryId, String name, boolean isPrivate, String guildId) {

    public static CategoryResponse from(final Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.isPrivate(), category.getGuildId());
    }
}
