package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.response.GuildCreateResponse;
import com.asyncgate.guild_server.entity.GuildEntity;
import com.asyncgate.guild_server.mapper.GuildMapper;
import com.asyncgate.guild_server.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;

    @Override
    @Transactional
    public GuildCreateResponse create(final GuildCreateRequest request) {
        Guild guild = Guild.create(request.getName(), request.isPrivate());
        GuildEntity guildEntity = GuildMapper.toEntity(guild);
        guildRepository.create(guildEntity);
        return GuildCreateResponse.of(guildEntity.getId(), guildEntity.getName(), guildEntity.isPrivate());
    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }
}
