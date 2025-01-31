package com.asyncgate.guild_server.dto.response;


import java.util.List;

public record GuildInfoResponse(GuildResponse guild, List<CategoryResponse> categories, List<ChannelResponse> channels) {

    public static GuildInfoResponse of(GuildResponse guild, List<CategoryResponse> categories,
                                       List<ChannelResponse> channels) {
        return new GuildInfoResponse(guild, categories, channels);
    }
}
