package com.asyncgate.guild_server.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@Schema(description = "길드 생성 요청 DTO")
public class GuildRequest {

    @Schema(description = "길드 이름", example = "Knight's Order")
    @NotBlank(message = "길드 이름은 필수 입력 값입니다.")
    @Size(min = 3, max = 30, message = "길드 이름은 최소 3자 이상, 최대 30자 이하여야 합니다.")
    private String name;

    @Schema(description = "비공개 여부", example = "false")
    private boolean isPrivate;

    @Schema(description = "프로필 이미지", type = "string", format = "binary")
    private MultipartFile profileImage;
}
