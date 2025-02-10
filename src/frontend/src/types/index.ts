export interface YearMonthDay {
  year: string;
  month: string;
  day: string;
}

type ValueOf<T> = T[keyof T];

// eslint-disable-next-line @typescript-eslint/no-unused-vars
const ModalTypes = {
  BASIC: 'basic',
  WITH_FOOTER: 'withFooter',
};

export type ModalType = ValueOf<typeof ModalTypes>;

export interface BaseModalData {
  content: React.ReactNode;
}
