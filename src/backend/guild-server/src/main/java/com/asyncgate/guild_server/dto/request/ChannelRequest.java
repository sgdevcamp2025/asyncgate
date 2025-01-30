package com.asyncgate.guild_server.dto.request;

import com.asyncgate.guild_server.domain.ChannelType;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelRequest {

    private String name;
    private boolean isPrivate;
    private String guildId;
    private String categoryId;

    @Pattern(regexp = "VOICE|TEXT", message = "채널 타입은 VOICE 또는 TEXT만 가능합니다.")
    private String channelType;

    public ChannelType getChannelType() {
        return ChannelType.from(channelType);
    }
}
