package com.asyncgate.guild_server.dto.request;

import com.asyncgate.guild_server.domain.ChannelType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelCreateRequest {

    @NotBlank(message = "채널 이름은 비어있을 수 없습니다.")
    private String name;

    private boolean isPrivate;

    @NotBlank(message = "guildId는 필수입니다.")
    private String guildId;

    @NotBlank(message = "categoryId는 필수입니다.")
    private String categoryId;

    @Pattern(regexp = "VOICE|TEXT", message = "채널 타입은 VOICE 또는 TEXT만 가능합니다.")
    private String channelType;

    public ChannelType getChannelType() {
        return ChannelType.from(channelType);
    }
}
