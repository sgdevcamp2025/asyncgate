package com.asyncgate.guild_server.security.filter;

import com.asyncgate.guild_server.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.util.Arrays;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final @NonNull HttpServletResponse response,
            final @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        System.out.println("==== JwtFilter Start ====");
        System.out.println("requestURI = " + requestURI);
        System.out.println("Thread: " + Thread.currentThread().getName());

        if (isPublicUri(requestURI)) {
            System.out.println("public!");
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        jwtService.authenticate(jwtToken);
        filterChain.doFilter(request, response);
    }

    private boolean isPublicUri(final String requestURI) {
        return Arrays.stream(SecurityConstants.PUBLIC_ENDPOINTS)
                .anyMatch(requestURI::startsWith);
    }
}
