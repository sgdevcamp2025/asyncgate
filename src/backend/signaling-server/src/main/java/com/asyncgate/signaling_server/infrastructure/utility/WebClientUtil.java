package com.asyncgate.signaling_server.infrastructure.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class WebClientUtil {
    private final WebClient.Builder webClientBuilder;

    private WebClient getClient() {
        return webClientBuilder.build();
    }

    public <T> Mono<T> get(String baseUrl, String path, Map<String, String> queryParams, ParameterizedTypeReference<T> responseType) {
        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> {
                    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl + path);
                    if (queryParams != null) {
                        queryParams.forEach(builder::queryParam);
                    }
                    return builder.build().toUri();
                })
                .retrieve()
                .bodyToMono(responseType);
    }
}