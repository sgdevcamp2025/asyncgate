package com.asyncgate.signaling_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.lang.Nullable;

public record JoinRoomRequest(

        @JsonProperty("audio_enabled")
        boolean audioEnabled,

        @JsonProperty("media_enabled")
        boolean mediaEnabled,

        @JsonProperty("data_enabled")
        boolean dataEnabled,

        @Nullable
        @JsonProperty("sdp_offer")
        String sdpOffer
) {
}
