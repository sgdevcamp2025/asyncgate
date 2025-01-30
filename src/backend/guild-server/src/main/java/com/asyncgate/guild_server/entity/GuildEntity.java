package com.asyncgate.guild_server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "guild")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuildEntity extends BaseEntity {

    @Id
    private String id;

    @Column(nullable = false, length = 30)
    private String name;
    private boolean isPrivate;

    @Builder
    private GuildEntity(final String id, final String name, final boolean isPrivate) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
    }
}
