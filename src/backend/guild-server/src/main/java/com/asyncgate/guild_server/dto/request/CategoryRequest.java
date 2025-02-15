package com.asyncgate.guild_server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "카테고리 생성 요청 DTO")
public class CategoryRequest {

    @Schema(description = "카테고리 이름", example = "Raid Planning")
    @NotBlank(message = "카테고리 이름은 필수 입력 값입니다.")
    @Size(min = 3, max = 30, message = "카테고리 이름은 최소 3자 이상, 최대 30자 이하여야 합니다.")
    private String name;

    @Schema(description = "길드 ID", example = "guild-12345")
    private String guildId;

    @Schema(description = "비공개 여부", example = "false")
    private boolean isPrivate;
}
