package com.asyncgate.guild_server.dto.response;

import com.asyncgate.guild_server.domain.ChannelType;
import java.util.List;

public record GuildInfoResponse(
        GuildResponse guild,
        List<InnerCategoryResponse> categories,
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

    private record InnerCategoryResponse(String categoryId, String name, boolean isPrivate) {
    }

    private record InnerChannelResponse(
            String channelId, String name, String topic,
            ChannelType channelType, boolean isPrivate
    ) {
    }
}
