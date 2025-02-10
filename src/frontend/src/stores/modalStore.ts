import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

import { BaseModalData, ModalType } from '../types';

type ModalState = {
  modal: { [K in ModalType]?: BaseModalData };
};

type ModalActions = {
  openModal: <T extends ModalType>(type: T, content: React.ReactNode) => void;
  closeModal: (type: ModalType, key: string) => void;
  closeAllModal: () => void;
};

export const useModalStore = create<ModalState & ModalActions>()(
  immer((set) => ({
    modal: {},
    openModal: (type, content) => {
      document.body.style.overflow = 'hidden';
      set((state) => {
        state.modal[type] = {
          content,
        };
      });
    },
    closeModal: (type) => {
      document.body.style.overflow = 'unset';
      set((state) => {
        if (state.modal[type]) {
          delete state.modal[type];
        }
      });
    },
    closeAllModal: () => set({ modal: {} }),
  })),
);

export default useModalStore;
