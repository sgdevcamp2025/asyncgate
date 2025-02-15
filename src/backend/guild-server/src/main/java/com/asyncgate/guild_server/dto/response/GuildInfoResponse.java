package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.ChannelType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "길드 상세 정보 응답 DTO")
public record GuildInfoResponse(
        @Schema(description = "길드 정보")
        GuildResponse guild,

        @Schema(description = "길드의 카테고리 목록")
        List<InnerCategoryResponse> categories,

        @Schema(description = "길드의 채널 목록")
        List<InnerChannelResponse> channels
) {

    public static GuildInfoResponse of(
            final GuildResponse guild,
            final List<CategoryResponse> categories,
            final List<ChannelResponse> channels
    ) {
        List<InnerCategoryResponse> innerCategories = transInnerCategory(categories);
        List<InnerChannelResponse> innerChannels = transInnerChannels(channels);
        return new GuildInfoResponse(guild, innerCategories, innerChannels);
    }

    private static List<InnerChannelResponse> transInnerChannels(List<ChannelResponse> channels) {
        return channels.stream().map(
                channel -> new InnerChannelResponse(channel.channelId(), channel.name(), channel.topic(), channel.channelType(), channel.isPrivate())
        ).toList();
    }

    private static List<InnerCategoryResponse> transInnerCategory(List<CategoryResponse> categories) {
        return categories.stream().map(
                category -> new InnerCategoryResponse(category.categoryId(), category.name(), category.isPrivate())
        ).toList();
    }

    @Schema(description = "길드 내 카테고리 정보 DTO")
    private record InnerCategoryResponse(
            @Schema(description = "카테고리 ID", example = "category-67890")
            String categoryId,

            @Schema(description = "카테고리 이름", example = "General Discussions")
            String name,

            @Schema(description = "비공개 여부", example = "false")
            boolean isPrivate
    ) {}

    @Schema(description = "길드 내 채널 정보 DTO")
    private record InnerChannelResponse(
            @Schema(description = "채널 ID", example = "channel-56789")
            String channelId,

            @Schema(description = "채널 이름", example = "General Chat")
            String name,

            @Schema(description = "채널 설명", example = "This is a general discussion channel.")
            String topic,

            @Schema(description = "채널 타입 (VOICE 또는 TEXT)", example = "TEXT")
            ChannelType channelType,

            @Schema(description = "비공개 여부", example = "false")
            boolean isPrivate
    ) {}
}
