package com.asyncgate.signaling_server.security.service;

import com.asyncgate.signaling_server.security.info.CustomUserPrincipal;
import com.asyncgate.signaling_server.security.usecase.AuthenticateJsonWebTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateJsonWebTokenService implements AuthenticateJsonWebTokenUseCase {

    @Override
    public CustomUserPrincipal execute(final String id) {
        // Member 조회 없이, id와 roles를 기반으로 CustomUserPrincipal 생성
        return CustomUserPrincipal.create(id);
    }
}
