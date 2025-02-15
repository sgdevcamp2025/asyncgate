package com.asyncgate.guild_server.controller.docs;

import com.asyncgate.guild_server.dto.request.GuildRequest;
import com.asyncgate.guild_server.dto.response.GuildInfoResponse;
import com.asyncgate.guild_server.dto.response.GuildResponse;
import com.asyncgate.guild_server.dto.response.GuildResponses;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Guild API", description = "길드 관련 API")
@SecurityRequirement(name = "JWT TOKEN")
@RequestMapping("/guilds")
public interface GuildControllerDocs {

    @Operation(summary = "길드 생성", description = "새로운 길드를 생성합니다.",
            requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(implementation = GuildRequest.class))))
    @PostMapping(consumes = "multipart/form-data")
    SuccessResponse<GuildResponse> create(
            @AuthenticationPrincipal String userId,
            @ModelAttribute GuildRequest request
    );

    @Operation(summary = "내 길드 목록 조회", description = "사용자가 속한 길드 목록을 조회합니다.")
    @GetMapping
    SuccessResponse<GuildResponses> getMyGuilds(
            @AuthenticationPrincipal String userId
    );

    @Operation(summary = "랜덤 길드 조회", description = "일정 개수만큼 랜덤 길드를 조회합니다.")
    @GetMapping("/rand")
    SuccessResponse<GuildResponses> getRand(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "조회할 길드 개수", required = false, example = "10")
            @RequestParam(required = false, defaultValue = "10") int limit
    );

    @Operation(summary = "길드 단일 조회", description = "특정 길드의 상세 정보를 조회합니다.")
    @GetMapping("/{guildId}")
    SuccessResponse<GuildInfoResponse> readOne(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId
    );

    @Operation(summary = "길드 정보 수정", description = "길드 정보를 수정합니다.",
            requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data",
                    schema = @Schema(implementation = GuildRequest.class))))
    @PatchMapping(value = "/{guildId}", consumes = "multipart/form-data")
    SuccessResponse<GuildResponse> update(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId,

            @ModelAttribute GuildRequest request
    );

    @Operation(summary = "길드 삭제", description = "사용자가 특정 길드를 삭제합니다.")
    @DeleteMapping("/{guildId}")
    SuccessResponse<String> delete(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId
    );
}
