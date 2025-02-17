package com.asyncgate.apigatewayserver.filter;

import com.asyncgate.apigatewayserver.config.CorsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
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

        logger.info(request.getURI().toString());
        logger.info(request.getMethod().name());

        if (request.getMethod().matches(OPTIONS_HEADERS)) {
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(", ", ALLOWED_METHODS));
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
            exchange.getResponse().getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOW_CREDENTIALS);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
