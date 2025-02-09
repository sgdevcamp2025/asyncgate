import DropdownInput from '../DropdownInput';

import * as S from './styles';
import useDateInput from './useDateInput';

interface AuthDateInputProps {
  id: string;
  label: string;
  isRequired?: boolean;
}

const AuthDateInput = ({ label, isRequired }: AuthDateInputProps) => {
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
  } = useDateInput();

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
            ref={monthRef}
            items={months}
            placeholder="월"
            selectedItem={selectedMonth}
            handleSelect={handleMonthSelect}
          />
        </S.MonthInput>
        <S.DayInput>
          <DropdownInput
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
