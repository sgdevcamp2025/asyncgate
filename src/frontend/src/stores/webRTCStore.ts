import { Client } from '@stomp/stompjs';
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface WebRTCState {
  stompClient: Client | null;
  isStompConnected: boolean;
  roomId: string;
  setStompClient: (client: Client | null) => void;
  setRoomId: (value: string) => void;
  setIsStompConnected: (value: boolean) => void;
  disconnectStomp: () => void;
}

export const useWebRTCStore = create<WebRTCState>()(
  persist(
    (set, get) => ({
      stompClient: null,
      isStompConnected: false,
      roomId: '',

      setStompClient: (client: Client | null) => set({ stompClient: client }),
      setRoomId: (roomId) => set({ roomId }),
      setIsStompConnected: (isStompConnected) => set({ isStompConnected }),
      disconnectStomp: () => {
        const { stompClient } = get();
        if (stompClient) {
          stompClient.deactivate();
          set({ stompClient: null, isStompConnected: false });
          console.log('🔌 STOMP WebSocket 연결 해제 시도');
        }
      },
    }),
    {
      name: 'webRTCInfo',
      partialize: (state) => ({
        roomId: state.roomId,
        isStompConnected: state.isStompConnected,
      }),
    },
  ),
);
