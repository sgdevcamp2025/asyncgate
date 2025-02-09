import { useEffect, useRef, useState } from 'react';

import { YearMonthDay } from '@/types';

interface UseDateInputProps {
  initialValue: YearMonthDay;
  handleChange: (value: YearMonthDay) => void;
}

const useDateInput = ({ initialValue, handleChange }: UseDateInputProps) => {
  const MAX_AGE = 150;
  const MAX_MONTH = 12;
  const MAX_DAY = 31;

  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: MAX_AGE }, (_, index) => String(currentYear - index));
  const months = Array.from({ length: MAX_MONTH }, (_, index) => String(index + 1));
  const days = Array.from({ length: MAX_DAY }, (_, index) => String(index + 1));

  const [selectedYear, setSelectedYear] = useState(initialValue.year);
  const [selectedMonth, setSelectedMonth] = useState(initialValue.month);
  const [selectedDay, setSelectedDay] = useState(initialValue.day);

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

  useEffect(() => {
    handleChange({ year: selectedYear, month: selectedMonth, day: selectedDay });
  }, [selectedYear, selectedMonth, selectedDay]);

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
