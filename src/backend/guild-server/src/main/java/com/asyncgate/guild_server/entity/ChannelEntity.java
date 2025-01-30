package com.asyncgate.guild_server.entity;

import com.asyncgate.guild_server.domain.ChannelType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "channel")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChannelEntity extends BaseEntity {

    @Id
    private String id;
    private String guildId;
    private String categoryId;
    private String name;

    @Enumerated(EnumType.STRING)
    private ChannelType channelType;

    private boolean isPrivate;

    @Builder
    private ChannelEntity(
            String id, String guildId, String categoryId,
            String name, ChannelType channelType, boolean isPrivate
    ) {
        this.id = id;
        this.guildId = guildId;
        this.categoryId = categoryId;
        this.name = name;
        this.channelType = channelType;
        this.isPrivate = isPrivate;
    }
}
