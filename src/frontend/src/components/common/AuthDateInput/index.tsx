import { useState } from 'react';

import DropdownInput from '../DropdownInput';

import * as S from './styles';
import useDateInput from './useDateInput';

interface AuthDateInputProps {
  id: string;
  label: string;
  isRequired?: boolean;
}

const AuthDateInput = ({ label, isRequired }: AuthDateInputProps) => {
  const { years, months, days } = useDateInput();
  const [selectedYear, setSelectedYear] = useState('');
  const [selectedMonth, setSelectedMonth] = useState('');
  const [selectedDay, setSelectedDay] = useState('');

  const handleYearSelect = (year: string) => {
    setSelectedYear(year);
  };

  const handleMonthSelect = (month: string) => {
    setSelectedMonth(month);
  };

  const handleDaySelect = (day: string) => {
    setSelectedDay(day);
  };

  return (
    <S.DateInputContainer>
      <S.Label>
        {label} {isRequired && <S.RequiredMark>*</S.RequiredMark>}
      </S.Label>
      <S.InputContainer>
        <S.YearInput>
          <DropdownInput items={years} placeholder="년" selectedItem={selectedYear} handleSelect={handleYearSelect} />
        </S.YearInput>
        <S.MonthInput>
          <DropdownInput
            items={months}
            placeholder="월"
            selectedItem={selectedMonth}
            handleSelect={handleMonthSelect}
          />
        </S.MonthInput>
        <S.DayInput>
          <DropdownInput items={days} placeholder="일" selectedItem={selectedDay} handleSelect={handleDaySelect} />
        </S.DayInput>
      </S.InputContainer>
    </S.DateInputContainer>
  );
};

export default AuthDateInput;
