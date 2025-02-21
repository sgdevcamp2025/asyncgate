package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.dto.request.ChannelCreateRequest;
import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;
import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import com.asyncgate.guild_server.repository.CategoryRepository;
import com.asyncgate.guild_server.repository.ChannelRepository;
import com.asyncgate.guild_server.repository.GuildRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;
    private final GuildRepository guildRepository;

    @Override
    @Transactional
    public ChannelResponse create(final String userId, final ChannelCreateRequest request) {
        validGuild(request.getGuildId());
        validatePermission(userId, request.getGuildId());
        if (StringUtils.hasText(request.getCategoryId())) {
            validCategory(request.getCategoryId());
        }
        Channel channel = Channel.create(
                request.getGuildId(),
                request.getCategoryId(),
                request.getName(),
                request.getChannelType(),
                request.isPrivate()
        );
        channelRepository.save(channel);

        return ChannelResponse.from(channel);
    }

    private void validCategory(final String categoryId) {
        if (categoryId.equals(Channel.CATEGORY_ID_IS_NULL)) {
            throw new GuildServerException(FailType.CATEGORY_NOT_FOUND);
        }
        boolean categoryExists = categoryRepository.existsById(categoryId);
        if (!categoryExists) {
            throw new GuildServerException(FailType.CATEGORY_NOT_FOUND);
        }
    }

    private void validGuild(final String categoryId) {
        boolean guildExists = guildRepository.existsById(categoryId);
        if (!guildExists) {
            throw new GuildServerException(FailType.GUILD_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void delete(
            final String userId, final String guildId,
            final String channelId
    ) {
        validatePermission(userId, guildId);
        channelRepository.delete(channelId);
    }

    @Override
    @Transactional
    public ChannelResponse update(
            final String userId, final String guildId,
            final String channelId, final ChannelUpdateRequest request
    ) {
        validatePermission(userId, guildId);
        Channel channel = channelRepository.getById(channelId);
        channel.update(request);
        channelRepository.save(channel);
        return ChannelResponse.from(channel);
    }

    private void validatePermission(final String userId, final String guildId) {
        // ToDo 생성시 validate 추후 권한관리 생성 후 확인
        // Guild, Category, Channel 별로 권한을 생성해야하는가? 혹시 몰라 3개다 받도록 개발
    }
}
