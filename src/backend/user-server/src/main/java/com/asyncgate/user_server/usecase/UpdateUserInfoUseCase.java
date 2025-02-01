package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.dto.request.UpdateUserInfoRequest;
import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface UpdateUserInfoUseCase {
    /**
     * 사용자 정보 수정
     * @param userId
     * @param request
     */
    void execute(final String userId, final UpdateUserInfoRequest request);
}
