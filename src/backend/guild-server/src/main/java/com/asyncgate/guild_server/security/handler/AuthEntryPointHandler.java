package com.asyncgate.guild_server.security.handler;

import com.asyncgate.guild_server.exception.FailResponse;
import com.asyncgate.guild_server.exception.FailType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

// 인증 실패시 동작
@Component
public class AuthEntryPointHandler implements AuthenticationEntryPoint {

    private static final ObjectMapper handlerObjectMapper = new ObjectMapper();

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException
    ) throws IOException {
        writeResponse(response);
    }

    public void writeResponse(final HttpServletResponse response) throws IOException {
        FailType failType = FailType.JWT_INVALID_TOKEN;
        FailResponse failResponse = FailResponse.of(
                failType.getErrorCode(),
                failType.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(failType.getStatus().value());
        handlerObjectMapper.writeValue(response.getWriter(), failResponse);
    }
}