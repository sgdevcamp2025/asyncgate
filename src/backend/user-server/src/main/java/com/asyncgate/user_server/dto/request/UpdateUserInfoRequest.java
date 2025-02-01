package com.asyncgate.user_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.multipart.MultipartFile;

public record UpdateUserInfoRequest(

        @JsonProperty("name")
        String name,

        @JsonProperty("nickname")
        String nickname,

        @JsonProperty("profile_img")
        MultipartFile profileImage

        // 나중에 배너 이미지, 전화번호 추가될수도
) {
}
