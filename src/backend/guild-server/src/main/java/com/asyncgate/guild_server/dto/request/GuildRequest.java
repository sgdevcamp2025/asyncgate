package com.asyncgate.guild_server.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class GuildRequest {

    @NotBlank(message = "길드 이름은 필수 입력 값입니다.")
    @Size(min = 3, max = 30, message = "길드 이름은 최소 3자 이상, 최대 30자 이하여야 합니다.")
    private String name;

    private boolean isPrivate;

    private MultipartFile profileImage;
}
