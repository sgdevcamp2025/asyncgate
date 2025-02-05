import * as S from './styles';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  label: string;
  isRequired?: boolean;
  error?: string;
  type?: React.InputHTMLAttributes<HTMLInputElement>['type'];
  value?: string;
}

const Input = ({ label, isRequired, error, type = 'text', value, ...props }: InputProps) => {
  return (
    <S.InputContainer>
      <S.InputLabel>
        {label} {isRequired && <S.RequiredMark>*</S.RequiredMark>}
      </S.InputLabel>
      <S.Input type={type} value={value} {...props} />
      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.InputContainer>
  );
};

export default Input;
