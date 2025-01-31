package com.asyncgate.guild_server.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChannelUpdateRequest {

    @NotBlank(message = "채널 이름은 필수입니다.")
    private String name;

    @Max(value = 1024, message = "내용의 최대 길이는 1024자 입니다.")
    private String topic;

    private boolean isPrivate;
}
