package com.asyncgate.signaling_server.support.handler;

import com.asyncgate.signaling_server.dto.response.GetUsersInChannelResponse;
import com.asyncgate.signaling_server.entity.type.MemberMediaType;
import com.asyncgate.signaling_server.security.constant.Constants;
import com.asyncgate.signaling_server.security.utility.JsonWebTokenUtil;
import com.asyncgate.signaling_server.signaling.KurentoManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class KurentoHandler extends TextWebSocketHandler {

    private final KurentoManager kurentoManager;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    /**
     * 클라이언트가 WebSocket에 연결되었을 때 실행됨
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
        log.info("✅ WebSocket 연결됨: {}", session.getId());
    }

    /**
     * 클라이언트가 WebSocket 연결을 종료하면, 세션에서 제거
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("❌ WebSocket 연결 종료: sessionId={}, 상태코드={}, 이유={}", session.getId(), status.getCode(), status.getReason());

        // 특정 상태 코드에 따른 추가 처리
        if (status.getCode() == 1006) {
            log.warn("⚠️ 클라이언트에서 비정상적으로 연결이 종료됨 (예: 네트워크 문제, 서버 종료)");
        } else if (status.getCode() == 1011) {
            log.error("❌ 서버 내부 오류로 WebSocket 연결이 종료됨");
        }
    }
}