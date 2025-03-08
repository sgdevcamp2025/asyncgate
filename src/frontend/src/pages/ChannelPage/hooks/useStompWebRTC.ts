/* eslint-disable @typescript-eslint/no-explicit-any */
import { Client, Frame } from '@stomp/stompjs';
import { useEffect, useRef, useState } from 'react';

import { getUserId } from '@/api/users';

interface UseStompWebRTCProps {
  roomId: string;
  handleUsers: (users: any) => void;
  handleAnswer: (answer: any) => void;
  handleIceCandidate: (candidate: any) => void;
  handlePublish: (publisherId: any) => void;
}

const useStompWebRTC = ({
  roomId,
  handleUsers,
  handleAnswer,
  handleIceCandidate,
  handlePublish,
}: UseStompWebRTCProps) => {
  const [isConnected, setIsConnected] = useState(false);
  const stompClient = useRef<Client | null>(null);

  const SERVER_URL = import.meta.env.VITE_SIGNALING;
  const token = localStorage.getItem('access_token');

  if (!token) return;
  const userId = getUserId();

  if (!roomId) return;

  const client = new Client({
    webSocketFactory: () => new WebSocket(SERVER_URL, ['v10.stomp', token]),
    connectHeaders: { Authorization: `Bearer ${token}` },
    reconnectDelay: 5000,
    heartbeatIncoming: 10000,
    heartbeatOutgoing: 10000,

    onConnect: (frame: Frame) => {
      console.log('✅ STOMP 연결 성공!', frame);
      setIsConnected(true);

      // 연결 성공 시 subscribe
      client.subscribe(`/topic/users/${roomId}`, (message) => {
        const users = JSON.parse(message.body);
        console.log('📩 users 받은 메시지:', message);
        handleUsers(users);
      });

      client.subscribe(`/topic/answer/${roomId}/${userId}`, (message) => {
        const answer = JSON.parse(message.body);
        console.log('📩 answer 받은 메시지:', message);
        handleAnswer(answer.message);
      });

      client.subscribe(`/topic/candidate/${roomId}/${userId}`, (message) => {
        const candidate = JSON.parse(message.body);
        console.log('📩 candidate 받은 메시지:', message);
        handleIceCandidate(candidate.candidate);
      });

      client.subscribe(`/topic/publisher/${roomId}`, (message) => {
        const publisher_id = JSON.parse(message.body);
        console.log('📩 publisher 받은 메시지:', message);
        handlePublish(publisher_id);
      });
    },

    onWebSocketError: (error: Error) => {
      console.log('WebSocket 에러', error);
    },

    onStompError: (frame) => {
      console.error('❌ STOMP 오류 발생!', frame);
    },
  });

  client.activate();
  stompClient.current = client;

  return {
    // client.deactivate();
    // clientRef.current = null;
    // setIsConnected(false);
    // console.log('✅ WebSocket 연결 해제됨');
    client: stompClient.current,
    isConnected,
  };
};

export default useStompWebRTC;
