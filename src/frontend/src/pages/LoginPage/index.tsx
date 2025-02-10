import { useNavigate } from 'react-router-dom';

import AuthInput from '@/components/common/AuthInput';

import useLogin from './hooks/useLogin';
import * as S from './styles';

const LoginPage = () => {
  const navigate = useNavigate();
  const { email, password, handleEmailChange, handlePasswordChange } = useLogin();

  const handleRegisterButtonClick = () => {
    navigate('/register');
  };

  return (
    <>
      <S.PageContainer>
        <S.ContentWrapper>
          <S.LoginFormContainer>
            <S.MainLoginContainer>
              <S.LoginFormHeader>
                <S.HeaderTitle>돌아오신 것을 환영해요!</S.HeaderTitle>
                <S.HeaderSubtitle>다시 만나다니 너무 반가워요!</S.HeaderSubtitle>
              </S.LoginFormHeader>
              <S.InputContainer>
                <AuthInput
                  id="email"
                  label="이메일 또는 전화번호"
                  isRequired={true}
                  value={email}
                  handleChange={handleEmailChange}
                />
                <AuthInput
                  id="password"
                  label="비밀번호"
                  type="password"
                  isRequired={true}
                  value={password}
                  handleChange={handlePasswordChange}
                />
              </S.InputContainer>
              <S.ForgotPasswordButton>
                <S.LinkText>비밀번호를 잊으셨나요?</S.LinkText>
              </S.ForgotPasswordButton>
              <S.LoginButton>
                <S.LoginText>로그인</S.LoginText>
              </S.LoginButton>
              <S.ToggleRegisterContainer>
                <S.RegisterLabel>계정이 필요한가요?</S.RegisterLabel>
                <S.RegisterButton onClick={handleRegisterButtonClick}>
                  <S.LinkText>가입하기</S.LinkText>
                </S.RegisterButton>
              </S.ToggleRegisterContainer>
            </S.MainLoginContainer>
            <S.QRCodeLoginContainer>
              <S.QRCodeWrapper>
                <S.QRCodeImage />
              </S.QRCodeWrapper>
              <S.QRCodeLoginTitle>QR 코드로 로그인</S.QRCodeLoginTitle>
              <S.QRCodeLoginLabel>
                <strong>AsyncGate 모바일 앱</strong>으로 스캔해 <br />
                바로 로그인하세요.
              </S.QRCodeLoginLabel>
              <S.PassKeyLoginButton>
                <S.LinkText>또는, 패스키로 로그인하세요</S.LinkText>
              </S.PassKeyLoginButton>
            </S.QRCodeLoginContainer>
          </S.LoginFormContainer>
        </S.ContentWrapper>
      </S.PageContainer>
    </>
  );
};

export default LoginPage;
