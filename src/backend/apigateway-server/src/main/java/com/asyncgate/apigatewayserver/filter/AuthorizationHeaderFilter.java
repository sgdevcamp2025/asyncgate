package com.asyncgate.apigatewayserver.filter;

import com.asyncgate.apigatewayserver.jwt.JwtTokenProvider;
import com.asyncgate.apigatewayserver.support.response.FailResponse;
import com.asyncgate.apigatewayserver.support.response.FailType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    private static final String BEARER_PREFIX = "Bearer ";
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    public AuthorizationHeaderFilter(
            final JwtTokenProvider jwtTokenProvider,
            final ObjectMapper objectMapper
    ) {
        super(Config.class);
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, FailType.AUTHORIZATION_MISSING_HEADER);
            }

            String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
                return onError(exchange, FailType.AUTHORIZATION_INVALID_FORMAT);
            }

            String jwt = authorizationHeader.replace(BEARER_PREFIX, "");

            if (!jwtTokenProvider.isJwtValid(jwt)) {
                return onError(exchange, FailType.INVALID_JWT_TOKEN);
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(
            final ServerWebExchange exchange,
            final FailType failType
    ) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(failType.getStatus());
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        FailResponse failResponse = FailResponse.of(
                failType.getErrorCode(),
                failType.getMessage(),
                failType.getStatus().value()
        );

        try {
            String responseBody = objectMapper.writeValueAsString(failResponse);
            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            log.error("Failed to create error response", e);
            return response.setComplete();
        }
    }

    public static class Config {
    }
}
