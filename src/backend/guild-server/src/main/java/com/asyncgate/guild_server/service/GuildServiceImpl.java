package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.request.GuildUpdateRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;
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
    public GuildResponse create(final GuildCreateRequest request) {
        Guild guild = Guild.create(request.getName(), request.isPrivate());
        guildRepository.save(guild);
        return GuildResponse.of(guild.getId(), guild.getName(), guild.isPrivate());
    }

    @Override
    public void delete() {

    }

    @Override
    @Transactional
    public GuildResponse update(final String guildId, final GuildUpdateRequest request) {
        Guild guild = guildRepository.getById(guildId);
        Guild updateGuild = guild.update(request.getName(), request.isPrivate());
        guildRepository.save(updateGuild);
        return GuildResponse.of(guild.getId(), guild.getName(), guild.isPrivate());
    }
}
