package com.asyncgate.user_server.usecase;

import com.asyncgate.user_server.support.annotation.UseCase;

@UseCase
public interface DeleteUserUseCase {

        void execute(final String userId);
}
