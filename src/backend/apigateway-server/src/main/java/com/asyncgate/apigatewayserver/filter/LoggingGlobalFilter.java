package com.asyncgate.apigatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingGlobalFilter implements GlobalFilter {

    private static final Logger requestLogger = LoggerFactory.getLogger("HttpRequestLog");
    private static final Logger responseLogger = LoggerFactory.getLogger("HttpResponseLog");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MDC.put("method", request.getMethod().name());
        MDC.put("requestUri", request.getURI().toString());
        MDC.put("sourceIp", request.getHeaders().getFirst("X-Real-IP") != null
                ? request.getHeaders().getFirst("X-Real-IP")
                : request.getRemoteAddress() != null ? request.getRemoteAddress().toString() : "unknown");
        MDC.put("userAgent", request.getHeaders().getFirst("User-Agent"));
        MDC.put("xForwardedFor", request.getHeaders().getFirst("X-Forwarded-For"));
        MDC.put("xForwardedProto", request.getHeaders().getFirst("X-Forwarded-Proto"));
        String requestId = UUID.randomUUID().toString();
        MDC.put("requestId", requestId);
        MDC.put("startTime", String.valueOf(System.nanoTime()));

        requestLogger.info("Request received for URI: {}", request.getURI());

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    String startTimeStr = MDC.get("startTime");
                    long startTime = startTimeStr != null ? Long.parseLong(startTimeStr) : System.nanoTime();
                    double executionTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
                    MDC.put("responseTime", String.format("%.3f초", executionTime));
                    responseLogger.info("Response sent successfully for URI: {}", request.getURI());
                })
                .doFinally(signalType -> MDC.clear());
    }
}
