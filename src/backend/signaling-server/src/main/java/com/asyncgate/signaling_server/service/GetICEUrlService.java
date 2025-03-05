package com.asyncgate.signaling_server.service;

import com.asyncgate.signaling_server.config.ICEServerConfig;
import com.asyncgate.signaling_server.dto.response.GetICEUrlResponse;
import com.asyncgate.signaling_server.usecase.GetICEUrlUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetICEUrlService implements GetICEUrlUseCase {

    private final ICEServerConfig iceServerConfig;

    public GetICEUrlResponse execute(String type) {
        // type이 turn인 경우
        if ("turn".equals(type)) {
            return GetICEUrlResponse.builder()
                    .url(iceServerConfig.getTurnUrl())
                    .build();
        }

        // type이 sturn인 경우
        return GetICEUrlResponse.builder()
                .url(iceServerConfig.getStunUrl())
                .build();
    }
}
