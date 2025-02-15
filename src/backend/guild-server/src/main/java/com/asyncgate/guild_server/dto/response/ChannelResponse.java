package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.Channel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
@Schema(description = "채널 응답 DTO")
public record ChannelResponse(
        @Schema(description = "채널 ID", example = "channel-56789")
        String channelId,

        @Schema(description = "채널 이름", example = "General Chat")
        String name,

        @Schema(description = "채널 설명", example = "This Channel is ~ ")
        String topic,

        @Schema(description = "비공개 여부", example = "false")
        boolean isPrivate,

        @Schema(description = "길드 ID", example = "guild-12345")
        String guildId,

        @Schema(description = "카테고리 ID", example = "category-67890")
        String categoryId,

        @Schema(description = "채널 타입 (VOICE 또는 TEXT)", example = "TEXT")
        String channelType
) {
    public static ChannelResponse from(final Channel channel) {
        return new ChannelResponse(
                channel.getId(),
                channel.getName(),
                channel.getTopic(),
                channel.isPrivate(),
                channel.getGuildId(),
                channel.getCategoryId(),
                channel.getChannelType().name()
        );
    }
}
