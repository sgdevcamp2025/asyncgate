import * as S from './styles';

interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  id: string;
  label: string;
  isRequired?: boolean;
  error?: string;
  type?: React.InputHTMLAttributes<HTMLInputElement>['type'];
  value?: string;
  handleChange: (value: string) => void;
}

const AuthInput = ({
  id,
  label,
  isRequired = false,
  error,
  type = 'text',
  value,
  handleChange,
  ...props
}: InputProps) => {
  return (
    <S.InputContainer id={id}>
      <S.InputLabel>
        {label} {isRequired && <S.RequiredMark>*</S.RequiredMark>}
      </S.InputLabel>
      <S.Input type={type} value={value} onChange={(event) => handleChange(event.target.value)} {...props} />
      {error && <S.ErrorText>{error}</S.ErrorText>}
    </S.InputContainer>
  );
};

export default AuthInput;
