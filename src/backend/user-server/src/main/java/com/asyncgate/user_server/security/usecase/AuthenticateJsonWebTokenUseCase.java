package com.asyncgate.user_server.security.usecase;


import com.asyncgate.user_server.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticateJsonWebTokenUseCase {

    CustomUserPrincipal execute(String memberId);
}