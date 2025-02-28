package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.response.UserClientInfoResponses;
import com.asyncgate.user_server.dto.response.UserClientInfoResponses.UserClientInfoResponse;

import java.util.List;

public interface FindUserInfoUseCase {

    UserClientInfoResponses getByUserIds(List<String> memberIds);

    UserClientInfoResponse getByUserId(String userId);
}
