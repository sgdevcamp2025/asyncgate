package com.asyncgate.user_server.security.filter;

import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import com.asyncgate.user_server.security.exception.CommonException;
import com.asyncgate.user_server.security.exception.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SecurityException e) {
            log.error("FilterException throw SecurityException : {}", e.getMessage());
            request.setAttribute("exception", FailType.ACCESS_DENIED);
            filterChain.doFilter(request, response);
        } catch (CommonException e) {
            log.error("FilterException throw JsonWebTokenException : {}", e.getMessage());
            request.setAttribute("exception", e.getMessage());
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("FilterException throw Exception : {}", e.getMessage());
            request.setAttribute("exception", FailType._UNKNOWN_ERROR);
            filterChain.doFilter(request, response);
        }
    }
}