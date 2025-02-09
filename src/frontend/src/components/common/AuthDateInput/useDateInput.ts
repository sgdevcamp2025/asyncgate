import { useRef, useState } from 'react';

const useDateInput = () => {
  const MAX_AGE = 150;
  const MAX_MONTH = 12;
  const MAX_DAY = 31;

  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: MAX_AGE }, (_, index) => String(currentYear - index));
  const months = Array.from({ length: MAX_MONTH }, (_, index) => String(index + 1) + '월');
  const days = Array.from({ length: MAX_DAY }, (_, index) => String(index + 1));

  const [selectedYear, setSelectedYear] = useState('');
  const [selectedMonth, setSelectedMonth] = useState('');
  const [selectedDay, setSelectedDay] = useState('');

  const monthRef = useRef<HTMLInputElement>(null);
  const dayRef = useRef<HTMLInputElement>(null);

  const handleYearSelect = (year: string) => {
    setSelectedYear(year);
    monthRef.current?.focus();
  };

  const handleMonthSelect = (month: string) => {
    setSelectedMonth(month);
    dayRef.current?.focus();
  };

  const handleDaySelect = (day: string) => {
    setSelectedDay(day);
  };

  return {
    years,
    months,
    days,
    selectedYear,
    selectedMonth,
    selectedDay,
    monthRef,
    dayRef,
    handleYearSelect,
    handleMonthSelect,
    handleDaySelect,
  };
};

export default useDateInput;
