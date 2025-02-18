package com.asyncgate.guild_server.support.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
public class RequestResponseLoggingAspect {

    private static final Logger requestLogger = LoggerFactory.getLogger("HttpRequestLog");
    private static final Logger responseLogger = LoggerFactory.getLogger("HttpResponseLog");

    @Pointcut("execution(* com.asyncgate..controller..*Controller.*(..))")
    public void apiControllerMethods() {
    }

    @Before("apiControllerMethods()")
    public void logRequest(JoinPoint joinPoint) {
        setMDC();
        requestLogger.info("Request received for method: {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "apiControllerMethods()")
    public void logResponse() {
        String startTimeStr = MDC.get("startTime");
        long startTime = startTimeStr != null ? Long.parseLong(startTimeStr) : 0L;
        double executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
        MDC.put("responseTime", String.format("%.3f초", executionTime));
        responseLogger.info("Response sent successfully");
    }

    @After("apiControllerMethods()")
    public void clearMDC() {
        MDC.clear();
    }

    private void setMDC() {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            MDC.put("method", request.getMethod());
            MDC.put("requestUri", request.getRequestURI());
            MDC.put("sourceIp", request.getHeader("X-Real-IP") != null ? request.getHeader("X-Real-IP") : request.getRemoteAddr());
            MDC.put("userAgent", request.getHeader("User-Agent"));
            MDC.put("xForwardedFor", request.getHeader("X-Forwarded-For"));
            MDC.put("xForwardedProto", request.getHeader("X-Forwarded-Proto"));
            MDC.put("requestId", UUID.randomUUID().toString());
            MDC.put("startTime", String.valueOf(System.nanoTime()));
        }
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }
}
