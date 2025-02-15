package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Guild;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "길드 목록 응답 DTO")
public record GuildResponses(
        @Schema(description = "길드 목록")
        List<InnerGuildResponse> responses
) {

    public static GuildResponses from(final List<Guild> guilds) {
        List<InnerGuildResponse> innerGuilds = transInnerGuilds(guilds);
        return new GuildResponses(innerGuilds);
    }

    private static List<InnerGuildResponse> transInnerGuilds(final List<Guild> guilds) {
        return guilds.stream()
                .map(
                        guild -> new InnerGuildResponse(
                                guild.getId(), guild.getName(), guild.getProfileImageUrl()
                        )
                )
                .toList();
    }

    @Schema(description = "길드 간략 정보 응답 DTO")
    private record InnerGuildResponse(
            @Schema(description = "길드 ID", example = "guild-12345")
            String guildId,

            @Schema(description = "길드 이름", example = "Knight's Order")
            String name,

            @Schema(description = "프로필 이미지 URL", example = "https://cdn.example.com/images/guild123.png")
            String profileImageUrl
    ) {}
}
