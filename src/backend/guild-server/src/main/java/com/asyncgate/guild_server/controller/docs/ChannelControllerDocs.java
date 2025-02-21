package com.asyncgate.guild_server.controller.docs;

import com.asyncgate.guild_server.dto.request.ChannelCreateRequest;
import com.asyncgate.guild_server.dto.request.ChannelUpdateRequest;
import com.asyncgate.guild_server.dto.response.ChannelResponse;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Channel API", description = "채널 관련 API")
@SecurityRequirement(name = "JWT TOKEN")
public interface ChannelControllerDocs {

    @Operation(summary = "채널 생성", description = "새로운 채널을 생성합니다.")
    @PostMapping
    SuccessResponse<ChannelResponse> create(
            @AuthenticationPrincipal String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "채널 생성 요청 데이터",
                    required = true
            )
            @RequestBody ChannelCreateRequest request
    );

    @Operation(summary = "채널 삭제", description = "길드 내 특정 카테고리를 삭제합니다.")
    @DeleteMapping("/{guildId}/{channelId}")
    SuccessResponse<String> delete(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId,

            @Parameter(description = "채널 ID", required = true, example = "channel-56789")
            @PathVariable String channelId
    );

    @Operation(summary = "채널 수정", description = "특정 채널의 정보를 수정합니다.")
    @PatchMapping("/{guildId}/{channelId}")
    SuccessResponse<ChannelResponse> update(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId,

            @Parameter(description = "채널 ID", required = true, example = "channel-56789")
            @PathVariable String channelId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "채널 업데이트 요청 데이터",
                    required = true
            )
            @RequestBody ChannelUpdateRequest request
    );
}
