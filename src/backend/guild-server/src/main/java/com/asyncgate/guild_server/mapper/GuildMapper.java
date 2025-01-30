package com.asyncgate.guild_server.mapper;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.entity.GuildEntity;

public class GuildMapper {

    public static GuildEntity toEntity(Guild guild) {
        return new GuildEntity(guild.getId(), guild.getName(), guild.isPrivate());
    }

    public static Guild toDomain(GuildEntity entity) {
        return new Guild(entity.getId(), entity.getName(), entity.isPrivate());
    }
}
