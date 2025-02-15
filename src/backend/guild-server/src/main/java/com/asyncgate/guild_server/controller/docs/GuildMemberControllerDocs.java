package com.asyncgate.guild_server.controller.docs;

import com.asyncgate.guild_server.support.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Guild Member API", description = "길드 멤버 관련 API")
@SecurityRequirement(name = "JWT TOKEN")
@RequestMapping("/guilds")
public interface GuildMemberControllerDocs {

    @Operation(summary = "길드 초대 전송", description = "길드 관리자가 특정 유저에게 초대 요청을 보냅니다.")
    @PostMapping("/{guildId}/invitations")
    SuccessResponse<String> sendInvitation(
            @AuthenticationPrincipal String adminUserId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId,

            @Parameter(description = "초대 대상 사용자 ID", required = true, example = "user-56789")
            @RequestParam String targetUserId
    );

    @Operation(summary = "길드 초대 수락", description = "사용자가 초대를 수락하여 길드에 가입합니다.")
    @PatchMapping("/{guildId}/invitations/accept")
    SuccessResponse<String> acceptInvitation(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId
    );

    @Operation(summary = "길드 초대 거절", description = "사용자가 초대를 거절합니다.")
    @PatchMapping("/{guildId}/invitations/reject")
    SuccessResponse<String> rejectInvitation(
            @AuthenticationPrincipal String userId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId
    );

    @Operation(summary = "길드 초대 취소", description = "길드 관리자가 특정 유저의 초대를 취소합니다.")
    @DeleteMapping("/{guildId}/invitations")
    SuccessResponse<String> cancelInvitation(
            @AuthenticationPrincipal String adminUserId,

            @Parameter(description = "길드 ID", required = true, example = "guild-12345")
            @PathVariable String guildId
    );
}
