import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface ChannelActionState {
  isInVoiceChannel: boolean;
  isSharingScreen: boolean;
  isVideoOn: boolean;
  isMicOn: boolean;
  setIsInVoiceChannel: (value: boolean) => void;
  setIsSharingScreen: (value: boolean) => void;
  setIsVideoOn: (value: boolean) => void;
  setIsMicOn: (value: boolean) => void;
}

export const useChannelActionStore = create<ChannelActionState>()(
  persist(
    (set) => ({
      isInVoiceChannel: false,
      isSharingScreen: false,
      isVideoOn: false,
      isMicOn: false,
      setIsInVoiceChannel: () => set((state) => ({ isInVoiceChannel: !state.isInVoiceChannel })),
      setIsSharingScreen: () => set((state) => ({ isSharingScreen: !state.isSharingScreen })),
      setIsVideoOn: () => set((state) => ({ isVideoOn: !state.isVideoOn })),
      setIsMicOn: () => set((state) => ({ isMicOn: !state.isMicOn })),
    }),
    {
      name: 'channelAction',
    },
  ),
);
