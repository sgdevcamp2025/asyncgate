import { create } from 'zustand';
import { immer } from 'zustand/middleware/immer';

import { BaseModalData, ModalType } from '../components/common/Modal';

type ModalState = {
  modal: { [K in ModalType]?: { [key: string]: BaseModalData } };
};

type ModalActions = {
  openModal: <T extends ModalType>(type: T, key: string, content: React.ReactNode) => void;
  closeModal: (type: ModalType, key: string) => void;
  closeAllModal: () => void;
};

export const useModalStore = create<ModalState & ModalActions>()(
  immer((set) => ({
    modal: {},
    openModal: (type, key, content) => {
      document.body.style.overflow = 'hidden';
      set((state) => {
        if (!state.modal[type]) {
          state.modal[type] = {};
        }
        state.modal[type]![key] = {
          content,
        };
      });
    },
    closeModal: (type, key) => {
      document.body.style.overflow = 'unset';
      set((state) => {
        if (state.modal[type]?.[key]) {
          delete state.modal[type]![key];
        }
      });
    },
    closeAllModal: () => set({ modal: {} }),
  })),
);

export default useModalStore;
