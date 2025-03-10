package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Guild;
import com.asyncgate.guild_server.domain.GuildMember;
import com.asyncgate.guild_server.domain.GuildRole;
import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.*;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.repository.CategoryRepository;
import com.asyncgate.guild_server.repository.ChannelRepository;
import com.asyncgate.guild_server.repository.GuildMemberRepository;
import com.asyncgate.guild_server.repository.GuildRepository;
import com.asyncgate.guild_server.support.utility.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GuildServiceImpl implements GuildService {

    private final GuildRepository guildRepository;
    private final GuildMemberRepository guildMemberRepository;
    private final CategoryRepository categoryRepository;
    private final ChannelRepository channelRepository;
    private final S3Util s3Util;

    @Value("${cloud.aws.s3.profile.default.url}")
    private String defaultProfileImageUrl;

    @Override
    @Transactional
    public GuildResponse create(final String userId, final GuildRequest request) {
        String profileImageUrl = getProfileImageUrl(request.getProfileImage());
        Guild guild = Guild.create(request.getName(), request.isPrivate(), profileImageUrl);
        guildRepository.save(guild);

        GuildMember guildMember = GuildMember.createdByAdmin(userId, guild.getId());
        guildMemberRepository.save(guildMember);

        return GuildResponse.from(guild);
    }

    private String getProfileImageUrl(final MultipartFile profileImage) {
        if (profileImage != null && !profileImage.isEmpty()) {
            return s3Util.uploadFile(profileImage, Guild.class.getName());
        } else {
            return defaultProfileImageUrl;
        }
    }

    @Override
    @Transactional
    public void delete(final String userId, final String guildId) {
        validatePermission(userId, guildId);
        guildRepository.deleteById(guildId);
        guildMemberRepository.deleteAllByGuildId(guildId);
        categoryRepository.deleteAllByGuildId(guildId);
        channelRepository.deleteAllByGuildId(guildId);
    }

    private void validatePermission(final String userId, final String guildId) {
        GuildMember guildMember = guildMemberRepository.findAcceptedMemberByUserIdAndGuildId(userId, guildId);
        if (!guildMember.getGuildRole().equals(GuildRole.ADMIN)) {
            throw new GuildServerException(FailType.GUILD_PERMISSION_DENIED);
        }
    }

    @Override
    @Transactional
    public GuildResponse update(final String userId, final String guildId, final GuildRequest request) {
        Guild guild = guildRepository.getById(guildId);
        validatePermission(userId, guildId);

        String profileImageUrl = determineProfileImageUrl(request.getProfileImage(), guild.getProfileImageUrl());
        guild.update(request.getName(), request.isPrivate(), profileImageUrl);
        guildRepository.save(guild);
        return GuildResponse.from(guild);
    }

    @Override
    public GuildInfoResponse readOne(final String userId, final String guildId) {
        validGuildMember(userId, guildId);
        GuildResponse guildResponse = GuildResponse.from(guildRepository.getById(guildId));

        List<CategoryResponse> categoryResponses = categoryRepository.findActiveAllByGuildId(guildId).stream()
                .map(CategoryResponse::from)
                .toList();

        List<ChannelResponse> channelResponses = channelRepository.findActiveAllByGuildId(guildId).stream()
                .map(ChannelResponse::from)
                .toList();

        return GuildInfoResponse.of(guildResponse, categoryResponses, channelResponses);
    }

    @Override
    public GuildResponses readRand(final String userId, int limit) {
        List<String> guildIds = guildMemberRepository.findRandGuildIdsNotJoinedByUser(userId, limit);
        List<Guild> guilds = guildRepository.getByIds(guildIds);
        return GuildResponses.from(guilds);
    }

    @Override
    public GuildResponses readMyGuilds(final String userId) {
        List<String> guildIds = guildMemberRepository.findGuildIdsJoinedByUserId(userId);
        List<Guild> guilds = guildRepository.findAllByIds(guildIds);
        return GuildResponses.from(guilds);
    }

    private void validGuildMember(final String userId, final String guildId) {
        guildMemberRepository.findAcceptedMemberByUserIdAndGuildId(userId, guildId);
    }

    private String determineProfileImageUrl(final MultipartFile newProfileImage, final String currentProfileImageUrl) {
        // 클라이언트가 프로필을 변경하지 않았으므로 기존 이미지 유지
        if (newProfileImage == null) {
            return currentProfileImageUrl;
        }
        // 클라이언트가 기존 이미지를 삭제하려고 빈 파일을 보낸 경우 -> 기본 프로필로 변경
        if (newProfileImage.isEmpty()) {
            return defaultProfileImageUrl;
        }
        if (!currentProfileImageUrl.equals(defaultProfileImageUrl)) {
            // 기존 파일 삭제
            s3Util.deleteFile(currentProfileImageUrl);
        }
        return s3Util.uploadFile(newProfileImage, Guild.class.getName());
    }
}
