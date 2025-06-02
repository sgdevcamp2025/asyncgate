import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import { postLogin } from '@/api/users';
import AuthInput from '@/components/common/AuthInput';
import { useUserInfoStore } from '@/stores/userInfo';
import { formDropVarients } from '@/styles/motions';

import useLogin from './hooks/useLogin';
import * as S from './styles';

const LoginPage = () => {
  const navigate = useNavigate();
  const [errorMessage, setErrorMessage] = useState('');
  const { email, password, handleEmailChange, handlePasswordChange } = useLogin();
  const { setUserInfo } = useUserInfoStore();

  const handleRegisterButtonClick = () => {
    navigate('/register');
  };

  const handleLoginButtonClick = async () => {
    try {
      const response = await postLogin({ email, password });
      if (response.httpStatus === 200) {
        localStorage.setItem('access_token', response.result.access_token);
        setUserInfo({ userId: response.result.user_id });
        return navigate('/friends', { replace: true });
      } else if (response.httpStatus === 404) {
        return setErrorMessage('이메일이나 비밀번호를 확인해주세요.');
      }
    } catch (error) {
      console.error('로그인 요청 실패', error);
    }
  };

  return (
    <>
      <S.PageContainer>
        <S.ContentWrapper>
          <S.LoginFormContainer variants={formDropVarients} initial="hidden" animate="visible">
            <S.MainLoginContainer>
              <S.LoginFormHeader>
                <S.HeaderTitle>돌아오신 것을 환영해요!</S.HeaderTitle>
                <S.HeaderSubtitle>다시 만나다니 너무 반가워요!</S.HeaderSubtitle>
              </S.LoginFormHeader>
              <S.InputContainer>
                <AuthInput
                  id="email"
                  label="이메일 또는 전화번호"
                  type="email"
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
                <S.LoginText onClick={handleLoginButtonClick}>로그인</S.LoginText>
              </S.LoginButton>
              {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
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
