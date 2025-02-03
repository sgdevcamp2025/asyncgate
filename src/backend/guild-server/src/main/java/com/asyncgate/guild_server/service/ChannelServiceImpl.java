package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.dto.request.ChannelCreateRequest;
import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;
import com.asyncgate.guild_server.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelRepository channelRepository;

    @Override
    @Transactional
    public ChannelResponse create(final String userId, final ChannelCreateRequest request) {
        validatePermission(userId, request.getGuildId(), request.getCategoryId());
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

    @Override
    @Transactional
    public void delete(
            final String userId, final String guildId,
            final String categoryId, final String channelId
    ) {
        validatePermission(userId, guildId, categoryId);
        channelRepository.delete(categoryId);
    }

    @Override
    @Transactional
    public ChannelResponse update(
            final String userId, final String guildId, final String categoryId,
            final String channelId, final ChannelUpdateRequest request
    ) {
        validatePermission(userId, guildId, categoryId);
        Channel channel = channelRepository.getById(channelId);
        channel.update(request);
        channelRepository.save(channel);
        return ChannelResponse.from(channel);
    }

    private void validatePermission(final String userId, final String guildId, final String categoryId) {
        // ToDo 생성시 validate 추후 권한관리 생성 후 확인
        // Guild, Category, Channel 별로 권한을 생성해야하는가? 혹시 몰라 3개다 받도록 개발
    }
}
