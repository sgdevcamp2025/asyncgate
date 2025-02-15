package com.asyncgate.guild_server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "채널 수정 요청 DTO")
public class ChannelUpdateRequest {

    @Schema(description = "채널 이름", example = "Updated Channel Name", required = true)
    @NotBlank(message = "채널 이름은 필수입니다.")
    private String name;

    @Schema(description = "채널 설명 (최대 1024자)", example = "This is the updated topic for the channel.")
    @Max(value = 1024, message = "내용의 최대 길이는 1024자 입니다.")
    private String topic;

    @Schema(description = "비공개 여부", example = "true")
    private boolean isPrivate;
}
