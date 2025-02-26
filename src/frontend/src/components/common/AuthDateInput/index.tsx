import { motion } from 'motion/react';

import { descriptionVarients } from '@/styles/motions';
import { YearMonthDay } from '@/types';

import { Description } from '../AuthInput';
import DropdownInput from '../DropdownInput';

import * as S from './styles';
import useDateInput from './useDateInput';
interface AuthDateInputProps {
  label: string;
  isRequired?: boolean;
  initialValue: YearMonthDay;
  description?: Description | null;
  handleChange: (value: YearMonthDay) => void;
}

const AuthDateInput = ({ label, isRequired, initialValue, description, handleChange }: AuthDateInputProps) => {
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

  const isDescriptionDisplayed = description && description.type === 'error';

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
      {isDescriptionDisplayed && (
        <motion.div
          variants={descriptionVarients}
          initial="hidden"
          animate={isDescriptionDisplayed ? 'visible' : 'hidden'}
        >
          <S.DescriptionText $type={description.type}>{description.content}</S.DescriptionText>
        </motion.div>
      )}
    </S.DateInputContainer>
  );
};

export default AuthDateInput;
