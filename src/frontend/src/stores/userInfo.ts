import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export interface UserInfo {
  userId: string;
}

interface UserState {
  userInfo: UserInfo | null;
  setUserInfo: (user: UserInfo) => void;
  clearUserInfo: () => void;
}

export const useUserInfoStore = create<UserState>()(
  persist(
    (set) => ({
      userInfo: null,
      setUserInfo: (user) => set({ userInfo: user }),
      clearUserInfo: () => set({ userInfo: null }),
    }),
    {
      name: 'userInfo',
    },
  ),
);
