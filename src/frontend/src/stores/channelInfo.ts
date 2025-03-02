import { create } from 'zustand';
import { persist } from 'zustand/middleware';

import { ChannelType } from '@/types/guilds';

export interface GuildChannelInfo {
  id: string;
  name: string;
  type: ChannelType;
}

interface DMChannelInfo {
  id: string;
  name: string;
  type: ChannelType;
}

interface ChannelState {
  selectedChannel: GuildChannelInfo | null;
  selectedDMChannel: DMChannelInfo | null;
  setSelectedChannel: (channel: GuildChannelInfo | null) => void;
  setSelectedDMChannel: (channel: DMChannelInfo | null) => void;
}

export const useChannelInfoStore = create<ChannelState>()(
  persist(
    (set) => ({
      selectedChannel: null,
      selectedDMChannel: null,
      setSelectedChannel: (channel) => set({ selectedChannel: channel }),
      setSelectedDMChannel: (channel) => set({ selectedDMChannel: channel }),
    }),
    {
      name: 'channelInfo',
    },
  ),
);
