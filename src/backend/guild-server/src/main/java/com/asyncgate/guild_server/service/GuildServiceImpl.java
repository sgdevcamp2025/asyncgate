package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.domain.GuildMember;
import com.asyncgate.guild_server.domain.GuildRole;
import com.asyncgate.guild_server.dto.request.GuildCreateRequest;
import com.asyncgate.guild_server.dto.request.GuildUpdateRequest;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.repository.GuildMemberRepository;
import com.asyncgate.guild_server.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;

    @Override
    @Transactional
    public GuildResponse create(final String userId, final GuildCreateRequest request) {
        Guild guild = Guild.create(request.getName(), request.isPrivate());
        guildRepository.save(guild);

        GuildMember guildMember = GuildMember.join(userId, guild.getId(), GuildRole.ADMIN);
        guildMemberRepository.save(guildMember);

        return GuildResponse.of(guild.getId(), guild.getName(), guild.isPrivate());
    }

    @Override
    @Transactional
    public void delete(final String userId, final String guildId) {
        validateAdmin(userId, guildId);
        guildMemberRepository.softDeleteAllByGuildId(guildId);
        guildRepository.deleteById(guildId);
    }

    private void validateAdmin(final String userId, final String guildId) {
        GuildMember guildMember = guildMemberRepository.getByUserIdAndGuildId(userId, guildId);
        if (!guildMember.getGuildRole().equals(GuildRole.ADMIN)) {
            throw new GuildServerException(FailType.GUILD_PERMISSION_DENIED);
        }
    }

    @Override
    @Transactional
    public GuildResponse update(final String userId, final String guildId, final GuildUpdateRequest request) {
        Guild guild = guildRepository.getById(guildId);
        validateAdmin(userId, guildId);

        Guild updateGuild = guild.update(request.getName(), request.isPrivate());
        guildRepository.save(updateGuild);
        return GuildResponse.of(guild.getId(), guild.getName(), guild.isPrivate());
    }
}
