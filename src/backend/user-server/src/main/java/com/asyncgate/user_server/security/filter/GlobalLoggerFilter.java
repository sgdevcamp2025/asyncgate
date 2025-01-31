package com.asyncgate.user_server.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class GlobalLoggerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("[Global] HTTP Request Received! ({} {} {})",
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());

        request.setAttribute("INTERCEPTOR_PRE_HANDLE_TIME",  System.currentTimeMillis());

        filterChain.doFilter(request, response);

        Long preHandleTime = (Long) request.getAttribute("INTERCEPTOR_PRE_HANDLE_TIME");
        Long postHandleTime = System.currentTimeMillis();

        log.info("[Global] HTTP Request Has Been Processed! It Tokes {}ms. ({} {} {})",
                postHandleTime - preHandleTime,
                request.getHeader("X-FORWARDED-FOR") != null ? request.getHeader("X-FORWARDED-FOR") : request.getRemoteAddr(),
                request.getMethod(),
                request.getRequestURI());
    }
}