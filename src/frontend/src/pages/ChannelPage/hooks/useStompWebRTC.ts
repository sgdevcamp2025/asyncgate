import { Client, Frame } from '@stomp/stompjs';
import { useEffect, useRef, useState } from 'react';

interface UseStompWebRTCProps {
  roomId: string;
}

const useStompWebRTC = ({ roomId }: UseStompWebRTCProps) => {
  const [isConnected, setIsConnected] = useState(false);
  const clientRef = useRef<Client | null>(null);
  const connectionAttempts = useRef(0);

  const SERVER_URL = import.meta.env.VITE_SIGNALING;

  useEffect(() => {
    if (!roomId) return;

    const token = localStorage.getItem('access_token');
    if (!token) return;

    if (clientRef.current) {
      console.log('이전 STOMP 연결 정리 중...');
      clientRef.current.deactivate();
      clientRef.current = null;
    }

    connectionAttempts.current = 0;

    const client = new Client({
      webSocketFactory: () => new WebSocket(SERVER_URL, ['v10.stomp', token]),
      connectHeaders: { Authorization: `Bearer ${token}` },
      debug: (msg) => console.log('STOMP DEBUG:', msg),
      reconnectDelay: 5000,
      heartbeatIncoming: 4000,
      heartbeatOutgoing: 4000,

      onConnect: (frame: Frame) => {
        console.log('✅ STOMP 연결 성공!', frame);
        connectionAttempts.current = 0;
        setIsConnected(true);

        const subscriptions = [];

        // 연결 성공 시 subscribe
        subscriptions.push(
          client.subscribe(`/topic/users/${roomId}`, (message) => {
            console.log('📩 users 받은 메시지:', message);
          }),
        );

        subscriptions.push(
          client.subscribe(`/topic/answer/${roomId}`, (message) => {
            console.log('📩 answer 받은 메시지:', message);
          }),
        );

        subscriptions.push(
          client.subscribe(`/topic/candidate/${roomId}`, (message) => {
            console.log('📩 candidate 받은 메시지:', message);
          }),
        );
      },

      onWebSocketError: (error: Error) => {
        console.log('WebSocket 에러', error);
      },

      onStompError: (frame) => {
        console.error('❌ STOMP 오류 발생!', frame);
      },
    });

    client.activate();
    clientRef.current = client;

    return () => {
      client.deactivate();
      clientRef.current = null;
      setIsConnected(false);
      console.log('✅ WebSocket 연결 해제됨');
    };
  }, [roomId]);

  return { client: clientRef.current, isConnected };
};

export default useStompWebRTC;
