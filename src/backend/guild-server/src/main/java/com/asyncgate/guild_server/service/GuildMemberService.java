package com.asyncgate.guild_server.service;

public interface GuildMemberService {
    void sendInvitation(String adminUserId, String guildId, String targetUserId);
    void acceptInvitation(String userId, String guildId);
    void rejectInvitation(String userId, String guildId);
    void cancelInvitation(String adminUserId, String guildId);
}
