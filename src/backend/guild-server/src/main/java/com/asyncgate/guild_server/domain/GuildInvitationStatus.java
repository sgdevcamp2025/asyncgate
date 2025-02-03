package com.asyncgate.guild_server.domain;

public enum GuildInvitationStatus {

    // 초대 전송
    PENDING,
    // 초대 수락
    ACCEPTED,
    // 초대 거정
    REJECTED,
    // 초대 취소
    CANCELED,
    // 초대 만료
    EXPIRED,

    ;

}
