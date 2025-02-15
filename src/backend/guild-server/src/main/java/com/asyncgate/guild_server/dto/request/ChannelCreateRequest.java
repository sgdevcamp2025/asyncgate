package com.asyncgate.guild_server.dto.request;

import com.asyncgate.guild_server.domain.ChannelType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "채널 생성 요청 DTO")
public class ChannelCreateRequest {

    @Schema(description = "채널 이름", example = "General Chat")
    @NotBlank(message = "채널 이름은 비어있을 수 없습니다.")
    private String name;

    @Schema(description = "비공개 여부", example = "false")
    private boolean isPrivate;

    @Schema(description = "길드 ID", example = "guild-12345")
    @NotBlank(message = "guildId는 필수입니다.")
    private String guildId;

    @Schema(description = "카테고리 ID", example = "category-67890")
    @NotBlank(message = "categoryId는 필수입니다.")
    private String categoryId;

    @Schema(description = "채널 타입 (VOICE 또는 TEXT)", example = "TEXT")
    @Pattern(regexp = "VOICE|TEXT", message = "채널 타입은 VOICE 또는 TEXT만 가능합니다.")
    private String channelType;

    public ChannelType getChannelType() {
        return ChannelType.from(channelType);
    }
}
