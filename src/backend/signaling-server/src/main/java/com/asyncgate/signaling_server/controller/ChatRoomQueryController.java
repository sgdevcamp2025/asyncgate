package com.asyncgate.signaling_server.controller;

import com.asyncgate.signaling_server.dto.response.GetICEUrlResponse;
import com.asyncgate.signaling_server.support.response.SuccessResponse;
import com.asyncgate.signaling_server.usecase.GetICEUrlUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ice")
@RequiredArgsConstructor
public class ChatRoomQueryController {

    private final GetICEUrlUseCase getICEUrlUseCase;

    /**
     * ICE 서버 URL 조회 (TURN/STURN)
     */
    @GetMapping
    public SuccessResponse<GetICEUrlResponse> getICEServerUrl(@RequestParam("type") String type) {
        return SuccessResponse.ok(getICEUrlUseCase.execute(type));
    }
}
