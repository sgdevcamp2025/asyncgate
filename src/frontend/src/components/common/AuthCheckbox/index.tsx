import { ChangeEvent } from 'react';

import CheckedIcon from '@/assets/checked.svg';

import * as S from './styles';

interface AuthCheckboxProps {
  id: string;
  isChecked: boolean;
  handleChange?: (event: ChangeEvent<HTMLInputElement>) => void;
  label?: string;
  labelStyle?: React.CSSProperties;
}

const AuthCheckbox = ({ id, isChecked, handleChange, label, labelStyle }: AuthCheckboxProps) => {
  return (
    <S.CheckboxContainer id={id} onChange={handleChange}>
      <S.Checkbox $isChecked={isChecked}>
        <img src={CheckedIcon} alt="" />
      </S.Checkbox>
      {label && <S.Label $style={labelStyle}>{label}</S.Label>}
    </S.CheckboxContainer>
  );
};

export default AuthCheckbox;
