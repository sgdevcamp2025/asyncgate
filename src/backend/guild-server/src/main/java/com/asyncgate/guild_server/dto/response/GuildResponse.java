package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "길드 응답 DTO")
public record GuildResponse(
        @Schema(description = "길드 ID", example = "guild-12345")
        String guildId,

        @Schema(description = "길드 이름", example = "Knight's Order")
        String name,

        @Schema(description = "비공개 여부", example = "false")
        boolean isPrivate,

        @Schema(description = "프로필 이미지 URL", example = "https://cdn.example.com/images/guild123.png")
        String profileImageUrl
) {
    public static GuildResponse from(final Guild guild) {
        return new GuildResponse(guild.getId(), guild.getName(), guild.isPrivate(), guild.getProfileImageUrl());
    }
}
