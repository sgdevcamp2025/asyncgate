import { create } from 'zustand';
import { persist } from 'zustand/middleware';

type GuildState = {
  guildId: string;
  setGuildId: (guildId: string) => void;
};

export const useGuildInfoStore = create<GuildState>()(
  persist(
    (set) => ({
      guildId: '',
      setGuildId: (guildId) => set({ guildId }),
    }),
    {
      name: 'guild-info',
    },
  ),
);
