package com.asyncgate.signaling_server.security.filter;

import com.asyncgate.signaling_server.security.constant.Constants;
import com.asyncgate.signaling_server.security.utility.JsonWebTokenUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FilterChannelInterceptor implements ChannelInterceptor {

    private static final Logger log = LoggerFactory.getLogger(FilterChannelInterceptor.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final JsonWebTokenUtil jsonWebTokenUtil;

    public FilterChannelInterceptor(JsonWebTokenUtil jsonWebTokenUtil) {
        this.jsonWebTokenUtil = jsonWebTokenUtil;
    }

    private String extractToken(String headerValue) {
        if (headerValue == null || headerValue.trim().isEmpty()) {
            return null;
        }
        String token = headerValue.trim();
        return token.toLowerCase().startsWith(BEARER_PREFIX.toLowerCase()) ? token.substring(BEARER_PREFIX.length()) : token;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        log.info("📥 [STOMP] Command: {}, sessionId: {}", headerAccessor.getCommand(), headerAccessor.getSessionId());

        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            String rawAuth = headerAccessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
            log.info("🔑 [STOMP] Raw Authorization Header: {}", rawAuth);
            String jwtToken = extractToken(rawAuth);
            if (jwtToken == null || jwtToken.isEmpty()) {
                log.error("🚨 [STOMP] Access Token is missing or improperly formatted!");
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Access token is missing");
            }

            if(headerAccessor.getSessionAttributes().get("userId") == null) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "userId is missing");
            }
            log.info("✅ [STOMP] CONNECT 요청 처리 완료");
        }
        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("📡 [STOMP] Command: {}, sessionId: {}, sent: {}", accessor.getCommand(), accessor.getSessionId(), sent);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("✅ [STOMP] CONNECT 성공 - sessionId: {}", accessor.getSessionId());
            handleConnect(accessor);
            log.info("🔎 [STOMP] CONNECTED 프레임 헤더: {}", accessor.getMessageHeaders());
            accessor.getMessageHeaders().forEach((key, value) -> log.info("messageHeader = {}: {}", key, value));
        } else if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info("🔌 [STOMP] DISCONNECT 요청 - sessionId: {}", accessor.getSessionId());
            handleDisconnect(accessor);
        }
    }

    private void handleDisconnect(StompHeaderAccessor accessor) {
        log.info("🔌 [STOMP] WebSocket 연결 해제 - sessionId: {}", accessor.getSessionId());
    }

    private void handleConnect(StompHeaderAccessor accessor) {
        String currentSessionId = accessor.getSessionId();
        if (currentSessionId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not session now");
        }
        String rawAuth = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        String jwtToken = extractToken(rawAuth);
        if (jwtToken == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "JWT token is missing");
        }

        Claims claims = jsonWebTokenUtil.validate(jwtToken);

        String memberId = claims.get(Constants.MEMBER_ID_CLAIM_NAME, String.class);

        // LoginSessionRequest loginSessionRequest = new LoginSessionRequest(LoginType.LOGIN, currentSessionId, currentUserId);
        // StateRequest stateRequest = new StateRequest(StatusType.CONNECT, currentUserId);
        // ToDo: 상태관리 서버에 로그인 전달 (주석 유지)
        // val guildIds = guildClient.getGuildIds(jwtToken)
        // 시그널링 서버에 전달 (주석 유지)
        // messageSender.signaling(stateTopic, stateRequest)
    }
}