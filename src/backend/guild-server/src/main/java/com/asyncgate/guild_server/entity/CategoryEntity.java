package com.asyncgate.guild_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryEntity extends BaseEntity {

    @Id
    private String id;

    private String name;
    private boolean isPrivate;
    private String guildId;

    @Builder
    public CategoryEntity(String id, String name, boolean isPrivate, String guildId) {
        this.id = id;
        this.name = name;
        this.isPrivate = isPrivate;
        this.guildId = guildId;
    }
}
