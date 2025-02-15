package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카테고리 응답 DTO")
public record CategoryResponse(
        @Schema(description = "카테고리 ID", example = "category-12345")
        String categoryId,

        @Schema(description = "카테고리 이름", example = "Raid Planning")
        String name,

        @Schema(description = "비공개 여부", example = "false")
        boolean isPrivate,

        @Schema(description = "길드 ID", example = "guild-12345")
        String guildId
) {
    public static CategoryResponse from(final Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.isPrivate(), category.getGuildId());
    }
}
