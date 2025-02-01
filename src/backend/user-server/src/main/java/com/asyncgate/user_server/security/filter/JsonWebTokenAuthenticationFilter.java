package com.asyncgate.user_server.security.filter;

import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.security.usecase.AuthenticateJsonWebTokenUseCase;
import com.asyncgate.user_server.security.constant.Constants;
import com.asyncgate.user_server.security.exception.CommonException;
import com.asyncgate.user_server.security.info.CustomUserPrincipal;
import com.asyncgate.user_server.security.utility.HeaderUtil;
import com.asyncgate.user_server.security.utility.JsonWebTokenUtil;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT를 이용한 인증을 처리하는 필터
 */
@RequiredArgsConstructor
public class JsonWebTokenAuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticateJsonWebTokenUseCase authenticateJsonWebTokenUseCase;

    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String token = HeaderUtil.refineHeader(request, Constants.AUTHORIZATION_HEADER, Constants.BEARER_PREFIX)
                .orElseThrow(() -> new CommonException(FailType.INVALID_HEADER_ERROR));

        Claims claims = jsonWebTokenUtil.validate(token);

        String memberId = claims.get(Constants.MEMBER_ID_CLAIM_NAME, String.class);

        CustomUserPrincipal principal = authenticateJsonWebTokenUseCase.execute(memberId);

        // AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                principal,
                null,
                principal.getAuthorities()
        );

        // SecurityContext에 AuthenticationToken 저장
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticationToken);
        SecurityContextHolder.setContext(context);

        // memberId를 request에 추가
        request.setAttribute(Constants.MEMBER_ID_ATTRIBUTE_NAME, memberId);

        // 다음 필터로 전달
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        // 인증이 필요 없는 URL 목록에 포함되는지 확인
        return Constants.NO_NEED_AUTH_URLS.stream()
                .anyMatch(excludePattern -> requestURI.matches(excludePattern.replace("**", ".*")));
    }
}