package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.GuildInvitationStatus;
import com.asyncgate.guild_server.domain.GuildMember;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.repository.GuildMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildMemberServiceImpl implements GuildMemberService {

    private final GuildMemberRepository guildMemberRepository;

    @Override
    @Transactional
    public void sendInvitation(final String adminUserId, final String guildId, final String targetUserId) {
        validAdminPermission(adminUserId, guildId);
        GuildMember invitedMember = GuildMember.invited(targetUserId, guildId);
        guildMemberRepository.save(invitedMember);
    }

    @Override
    @Transactional
    public void acceptInvitation(final String userId, final String guildId) {
        GuildMember member = guildMemberRepository.findByUserIdAndGuildIdAndStatus(userId, guildId, GuildInvitationStatus.PENDING);
        member.accept();
        guildMemberRepository.save(member);
    }

    /**
     * 초대 거절
     */
    @Override
    @Transactional
    public void rejectInvitation(final String userId, final String guildId) {
        GuildMember member = guildMemberRepository.findByUserIdAndGuildIdAndStatus(userId, guildId, GuildInvitationStatus.PENDING);
        member.reject();
        guildMemberRepository.save(member);
    }

    /**
     * 초대 취소
     */
    @Override
    @Transactional
    public void cancelInvitation(final String adminUserId, final String guildId) {
        validAdminPermission(adminUserId, guildId);
        GuildMember member = guildMemberRepository.findByUserIdAndGuildIdAndStatus(adminUserId, guildId, GuildInvitationStatus.PENDING);
        member.cancel();
        guildMemberRepository.save(member);
    }

    private void validAdminPermission(final String adminUserId, final String guildId) {
        GuildMember adminMember = guildMemberRepository.findAcceptedMemberByUserIdAndGuildId(adminUserId, guildId);
        if (!adminMember.isAdmin()) {
            throw new GuildServerException(FailType.GUILD_PERMISSION_DENIED);
        }
    }
}

