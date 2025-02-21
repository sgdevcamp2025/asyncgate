package com.asyncgate.guild_server.domain;

import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
public class Channel implements Identifiable {
    private final String id;
    private final String guildId;
    private final String categoryId;
    private final ChannelType channelType;
    private String name;
    private String topic;
    private boolean isPrivate;

    @Builder
    private Channel(String id, String guildId, String categoryId, String name,
                    String topic, ChannelType channelType, boolean isPrivate) {
        this.id = id;
        this.guildId = guildId;
        this.categoryId = categoryId;
        this.name = name;
        this.topic = topic;
        this.channelType = channelType;
        this.isPrivate = isPrivate;
    }

    public static Channel create(
            final String guildId, final String categoryId, final String name,
            final ChannelType channelType, final boolean isPrivate
    ) {
        String id = UUID.randomUUID().toString();
        return Channel.builder()
                .id(id)
                .guildId(guildId)
                .categoryId(categoryId)
                .name(name)
                .channelType(channelType)
                .isPrivate(isPrivate)
                .build();
    }

    public void update(final ChannelUpdateRequest request) {
        changeName(request.getName());
        changePrivate(request.isPrivate());
        changeTopic(request.getTopic());
    }

    private void changeTopic(final String topic) {
        this.topic = topic;
    }

    private void changeName(final String name) {
        if (StringUtils.hasText(name) && !this.name.equals(name)) {
            this.name = name;
        }
    }

    private void changePrivate(final boolean isPrivate) {
        if (this.isPrivate != isPrivate) {
            this.isPrivate = isPrivate;
        }
    }

    public static String CATEGORY_ID_IS_NULL = "CATEGORY_ID_IS_NULL";
}
