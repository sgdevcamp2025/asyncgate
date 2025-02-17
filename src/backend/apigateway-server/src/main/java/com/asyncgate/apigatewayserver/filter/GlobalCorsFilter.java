package com.asyncgate.apigatewayserver.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static com.asyncgate.apigatewayserver.config.CorsConfig.*;

@Component
public class GlobalCorsFilter implements GlobalFilter {

    Logger logger = LoggerFactory.getLogger(GlobalCorsFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        logger.info("==== CORS Filter Start ====");
        logger.info("Request URI: {}", request.getURI());
        logger.info("Request Method: {}", request.getMethod().name());
        logger.info("Request Headers: {}", request.getHeaders());

        if ("OPTIONS".equalsIgnoreCase(request.getMethod().name())) {
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(", ", ALLOWED_METHODS));
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS);

            logger.info("Pre-flight OPTIONS request detected.");
            logger.info("Response Headers: {}", exchange.getResponse().getHeaders());

            exchange.getResponse().setStatusCode(HttpStatus.NO_CONTENT);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange).doOnSuccess(aVoid -> {
            logger.info("==== CORS Filter End ====");
            logger.info("Response Headers: {}", exchange.getResponse().getHeaders());
        });
    }
}

