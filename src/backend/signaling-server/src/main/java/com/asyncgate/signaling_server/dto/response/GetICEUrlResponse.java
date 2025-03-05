package com.asyncgate.signaling_server.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetICEUrlResponse {

    @JsonProperty("url")
    private final String url;

    @Builder
    public GetICEUrlResponse(final String url) {
        this.url = url;
    }
}
