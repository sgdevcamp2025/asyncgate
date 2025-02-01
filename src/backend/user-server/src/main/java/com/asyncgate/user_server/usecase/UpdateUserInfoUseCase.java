package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.UpdateUserInfoRequest;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface UpdateUserInfoUseCase {

    void execute(final String userId, final UpdateUserInfoRequest request);
}
