package com.asyncgate.signaling_server.usecase;

import com.asyncgate.signaling_server.dto.response.GetICEUrlResponse;

public interface GetICEUrlUseCase {
    /**
     * ICE 서버 URL 조회
     *
     */
    GetICEUrlResponse execute(final String type);
}
