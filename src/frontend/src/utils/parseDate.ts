import { YearMonthDay } from '@/types';

const parseDate = (value: YearMonthDay) => {
  const { year, month, day } = value;

  const parsedMonth = Number(month) < 10 ? '0' + month : month;
  const parsedDay = Number(day) < 10 ? '0' + day : day;

  return `${year}-${parsedMonth}-${parsedDay}`;
};

export default parseDate;
