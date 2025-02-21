package com.asyncgate.apigatewayserver.filter;

import com.asyncgate.apigatewayserver.exception.ApiGatewayServerException;
import com.asyncgate.apigatewayserver.jwt.JwtTokenProvider;
import com.asyncgate.apigatewayserver.support.response.FailResponse;
import com.asyncgate.apigatewayserver.exception.FailType;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    private static final List<String> NO_NEED_URLS = List.of(
        "/sign-up", "/sign-in", "/validation/email", "/validation/authentication-code"
    );

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
            String path = request.getURI().getPath();
            HttpHeaders headers = request.getHeaders();

            log.info("API Gateway 요청: [URI={}]", path);
            log.info("요청 헤더: {}", headers);

            if (NO_NEED_URLS.contains(path)) {
                return chain.filter(exchange);
            }

            if (!headers.containsKey(HttpHeaders.AUTHORIZATION)) {
                log.warn("Authorization 헤더 없음");
                return onError(exchange, FailType.AUTHORIZATION_MISSING_HEADER);
            }

            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
                log.warn("Authorization 헤더 형식 오류: {}", authorizationHeader);
                return onError(exchange, FailType.AUTHORIZATION_INVALID_FORMAT);
            }

            String jwt = authorizationHeader.replace(BEARER_PREFIX, "");
            log.info("JWT 토큰: {}", jwt);

            try {
                jwtTokenProvider.validate(jwt);
                log.info("JWT 유효성 검사 통과");
            } catch (ApiGatewayServerException e) {
                log.error("JWT 검증 실패: {}", e.getMessage());
                return onError(exchange, e.getFailType());
            }

            ServerHttpRequest modifiedRequest = exchange.getRequest()
                    .mutate()
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .build();

            return chain.filter(
                    exchange.mutate()
                            .request(modifiedRequest)
                            .build()
            );
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
