package com.asyncgate.guild_server.controller;

import com.asyncgate.guild_server.service.GuildMemberService;
import com.asyncgate.guild_server.support.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guilds")
@RequiredArgsConstructor
public class GuildMemberController {

    private final GuildMemberService guildMemberService;

    /**
     * 초대 생성 (길드 관리자가 특정 유저에게 초대 전송)
     */
    @PostMapping("/{guildId}/invitations")
    public SuccessResponse<String> sendInvitation(
            final @AuthenticationPrincipal String adminUserId,
            final @PathVariable String guildId,
            final @RequestParam String targetUserId
    ) {
        guildMemberService.sendInvitation(adminUserId, guildId, targetUserId);
        return SuccessResponse.created(String.format("User[%s]에게 초대가 성공적으로 전송되었습니다.", targetUserId));
    }

    /**
     *  초대 수락 (사용자가 초대를 수락하여 길드 가입)
     */
    @PatchMapping("/{guildId}/invitations/accept")
    public SuccessResponse<String> acceptInvitation(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        guildMemberService.acceptInvitation(userId, guildId);
        return SuccessResponse.ok(String.format("Guild[%s] 초대를 수락하였습니다.", guildId));
    }

    /**
     *  초대 거절 (사용자가 초대를 거절)
     */
    @PatchMapping("/{guildId}/invitations/reject")
    public SuccessResponse<String> rejectInvitation(
            final @AuthenticationPrincipal String userId,
            final @PathVariable String guildId
    ) {
        guildMemberService.rejectInvitation(userId, guildId);
        return SuccessResponse.ok(String.format("Guild[%s] 초대를 거절하였습니다.", guildId));
    }

    /**
     *  초대 취소 (길드 관리자가 초대를 취소)
     */
    @DeleteMapping("/{guildId}/invitations")
    public SuccessResponse<String> cancelInvitation(
            final @AuthenticationPrincipal String adminUserId,
            final @PathVariable String guildId
    ) {
        guildMemberService.cancelInvitation(adminUserId, guildId);
        return SuccessResponse.ok(String.format("Guild[%s] 초대가 취소되었습니다.", guildId));
    }

}
