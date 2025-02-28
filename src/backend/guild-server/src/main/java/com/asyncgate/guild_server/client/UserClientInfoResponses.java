package com.asyncgate.guild_server.client;

import java.time.LocalDate;
import java.util.List;

public record UserClientInfoResponses(List<UserClientInfoResponse> responses) {
    public record UserClientInfoResponse(String userId, String name, String nickname, String profileImageUrl, String email, LocalDate birth) {
    }
}
