package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface DeleteUserUseCase {

    /**
     * 사용자 삭제
     *
     * @param userId 사용자 아이디
     */
    void execute(final String userId);
}
