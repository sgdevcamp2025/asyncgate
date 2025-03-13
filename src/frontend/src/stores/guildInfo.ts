import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type GuildState = {
  guildId: string;
  guildName: string;
  setGuildId: (guildId: string) => void;
  setGuildName: (guildName: string) => void;
};

export const useGuildInfoStore = create<GuildState>()(
  persist(
    (set) => ({
      guildId: '',
      guildName: '',
      setGuildId: (guildId) => set({ guildId }),
      setGuildName: (guildName) => set({ guildName }),
    }),
    {
      name: 'guildInfo',
    },
  ),
);
