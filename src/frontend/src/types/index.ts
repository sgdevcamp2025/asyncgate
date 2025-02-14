export interface YearMonthDay {
  year: string;
  month: string;
  day: string;
}

export const ModalTypes = {
  modal: {
    basic: 'basic',
    withFooter: 'withFooter',
  },
  bottomSheet: {
    basic: 'basic',
  },
} as const;

type ValueOf<T> = T[keyof T];

export type ModalType = ValueOf<typeof ModalTypes.modal>;
export type BottomSheetType = ValueOf<typeof ModalTypes.bottomSheet>;
export type PopupType = ModalType | BottomSheetType;

export interface BasePopUpData {
  content: React.ReactNode;
}
