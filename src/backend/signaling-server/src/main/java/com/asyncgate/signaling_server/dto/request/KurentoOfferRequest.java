package com.asyncgate.signaling_server.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.kurento.client.IceCandidate;
import org.springframework.lang.Nullable;


public record KurentoOfferRequest(

        @JsonProperty("type")
        String type,

        @JsonProperty("data")
        KurentoOfferData data
) {
    public record KurentoOfferData(
            @JsonProperty("room_id")
            String roomId,

            @Nullable
            @JsonProperty("sdp_offer")
            String sdpOffer,

            @Nullable
            @JsonProperty("sdp_answer")
            String sdpAnswer,

            @Nullable
            @JsonProperty("candidate")
            IceCandidate candidate,

            @Nullable
            @JsonProperty("enabled")
            boolean enabled
    ) {}
}
