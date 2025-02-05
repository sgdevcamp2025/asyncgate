package com.asyncgate.signaling_server.security.usecase;


import com.asyncgate.signaling_server.security.info.CustomUserPrincipal;
import org.springframework.stereotype.Component;

@Component
public interface AuthenticateJsonWebTokenUseCase {

    CustomUserPrincipal execute(String memberId);
}