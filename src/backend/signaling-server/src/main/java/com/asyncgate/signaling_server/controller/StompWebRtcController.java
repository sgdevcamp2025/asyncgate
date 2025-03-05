package com.asyncgate.signaling_server.controller;

import com.asyncgate.signaling_server.dto.request.KurentoOfferRequest;
import com.asyncgate.signaling_server.signaling.KurentoManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompWebRtcController {

    private static final Logger log = LoggerFactory.getLogger(StompWebRtcController.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final KurentoManager kurentoManager;

    /**
     * WebRTC Offer 메시지 처리
     */
    @MessageMapping("/offer")
    public void handleOffer(KurentoOfferRequest message, StompHeaderAccessor accessor) {
        // 메시지 처리 및 Kurento에서 사용자 연결
        kurentoManager.processSdpOffer(message, accessor);

        kurentoManager.startIceCandidateListener(message, accessor);
    }

    /**
     *
     */
    @MessageMapping("/answer")
    public void handleAnswer(KurentoOfferRequest message, StompHeaderAccessor accessor) {
        // 메시지 처리 및 Kurento에서 사용자 연결
        kurentoManager.processSdpAnswer(message, accessor);
    }

    /**
     * ICE Candidate 메시지 처리
     */
    @MessageMapping("/candidate")
    public void handleIceCandidate(KurentoOfferRequest message, StompHeaderAccessor accessor) {

        System.out.println("addCandidate 접근 합니다 !!!!!!!!!!!!!!!!!!!!.");

        // Kurento에 ICE Candidate 전달
        kurentoManager.addIceCandidates(message, accessor);
    }

    /**
     * 미디어 토글 (AUDIO, MEDIA, DATA)
     */
    @MessageMapping("/toggle")
    public void toggleMediaState(KurentoOfferRequest message, StompHeaderAccessor accessor) {
        kurentoManager.updateUserMediaState(message, accessor);
    }

    /**
     * WebRTC 종료 처리
     */
    @MessageMapping("/exit")
    public void handleExit(KurentoOfferRequest message, StompHeaderAccessor accessor) {
        // Kurento에서 사용자 제거
        kurentoManager.removeUserFromChannel(message, accessor);
    }
}