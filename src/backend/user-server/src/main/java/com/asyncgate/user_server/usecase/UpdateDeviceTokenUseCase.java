package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.UpdateDeviceTokenRequest;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface UpdateDeviceTokenUseCase {

    void execute(final String userId, final UpdateDeviceTokenRequest request);
}
