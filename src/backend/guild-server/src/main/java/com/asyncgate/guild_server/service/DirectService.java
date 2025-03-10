package com.asyncgate.guild_server.service;

import com.asyncgate.guild_server.dto.request.DirectChannelCreateRequest;
import com.asyncgate.guild_server.dto.response.DirectResponse;
import com.asyncgate.guild_server.dto.response.DirectResponses;

import java.util.List;

public interface DirectService {
    DirectResponse create(String currentUserId, DirectChannelCreateRequest request);

    DirectResponses getDirectList(String currentUserId);

    List<String> getDirectDetail(String directId);
}
