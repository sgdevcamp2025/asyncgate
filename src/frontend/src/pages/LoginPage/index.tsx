import LogoIcon from '@/assets/logo.svg';
import AuthInput from '@/components/common/AuthInput';

import * as S from './styles';

const LoginPage = () => {
  return (
    <>
      <S.PageContainer>
        <S.Background />
        <S.Logo>
          <img src={LogoIcon} alt=""></img>
        </S.Logo>
        <S.ContentWrapper>
          <S.LoginFormContainer>
            <S.MainLoginContainer>
              <S.LoginFormHeader>
                <S.HeaderTitle>돌아오신 것을 환영해요!</S.HeaderTitle>
                <S.HeaderSubtitle>다시 만나다니 너무 반가워요!</S.HeaderSubtitle>
              </S.LoginFormHeader>
              <S.InputContainer>
                <AuthInput label="이메일 또는 전화번호" isRequired={true} />
                <AuthInput label="비밀번호" type="password" isRequired={true} />
              </S.InputContainer>
              <S.ForgotPasswordButton>
                <S.LinkText>비밀번호를 잊으셨나요?</S.LinkText>
              </S.ForgotPasswordButton>
              <S.LoginButton>
                <S.LoginText>로그인</S.LoginText>
              </S.LoginButton>
              <S.ToggleRegisterContainer>
                <S.RegisterText>계정이 필요한가요?</S.RegisterText>
                <S.RegisterButton>
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
