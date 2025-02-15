import { YearMonthDay } from '@/types';

import DropdownInput from '../DropdownInput';

import * as S from './styles';
import useDateInput from './useDateInput';
interface AuthDateInputProps {
  label: string;
  isRequired?: boolean;
  initialValue: YearMonthDay;
  handleChange: (value: YearMonthDay) => void;
}

const AuthDateInput = ({ label, isRequired, initialValue, handleChange }: AuthDateInputProps) => {
  const {
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
  } = useDateInput({ initialValue, handleChange });

  return (
    <S.DateInputContainer>
      <S.Label>
        {label} {isRequired && <S.RequiredMark>*</S.RequiredMark>}
      </S.Label>
      <S.InputContainer>
        <S.YearInput>
          <DropdownInput
            id="year"
            items={years}
            placeholder="년"
            selectedItem={selectedYear}
            handleSelect={handleYearSelect}
          />
        </S.YearInput>
        <S.MonthInput>
          <DropdownInput
            id="month"
            ref={monthRef}
            items={months}
            placeholder="월"
            selectedItem={selectedMonth}
            handleSelect={handleMonthSelect}
          />
        </S.MonthInput>
        <S.DayInput>
          <DropdownInput
            id="day"
            ref={dayRef}
            items={days}
            placeholder="일"
            selectedItem={selectedDay}
            handleSelect={handleDaySelect}
          />
        </S.DayInput>
      </S.InputContainer>
    </S.DateInputContainer>
  );
};

export default AuthDateInput;
