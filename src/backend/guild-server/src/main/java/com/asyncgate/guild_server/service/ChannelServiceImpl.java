package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.domain.Channel;
import com.asyncgate.guild_server.dto.request.ChannelRequest;
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
    public ChannelResponse create(final String userId, final ChannelRequest request) {
        validatePermission(userId, request.getGuildId(), request.getCategoryId());
        Channel channel = Channel.create(
                request.getGuildId(),
                request.getCategoryId(),
                request.getName(),
                request.getChannelType(),
                request.isPrivate()
        );
        channelRepository.create(channel);

        return ChannelResponse.of(
                channel.getId(),
                channel.getGuildId(),
                channel.getCategoryId(),
                channel.getName(),
                channel.getChannelType(),
                channel.isPrivate()
        );
    }

    @Override
    @Transactional
    public void delete(final String userId, final String guildId, final String categoryId, final String channelId) {
        validatePermission(userId, guildId, categoryId);
        channelRepository.delete(categoryId);
    }

    private void validatePermission(final String userId, final String guildId, final String categoryId) {
        // ToDo 생성시 validate 추후 권한관리 생성 후 확인
    }
}
