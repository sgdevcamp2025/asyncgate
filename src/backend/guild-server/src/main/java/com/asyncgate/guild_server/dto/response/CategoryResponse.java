package com.asyncgate.guild_server.dto.response;

public record CategoryResponse(String categoryId, String name, boolean isPrivate, String guildId) {

    public static CategoryResponse of(
            final String categoryId, final String name,
            final boolean isPrivate, final String guildId
    ) {
        return new CategoryResponse(categoryId, name, isPrivate, guildId);
    }
}
