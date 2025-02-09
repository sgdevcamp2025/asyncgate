import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import AuthCheckbox from '@/components/common/AuthCheckbox';
import AuthDateInput from '@/components/common/AuthDateInput';
import AuthInput from '@/components/common/AuthInput';
import { YearMonthDay } from '@/types';

import * as S from './styles';

const RegisterPage = () => {
  const navigate = useNavigate();
  const [isOptIn, setIsOptIn] = useState(false);
  const [email, setEmail] = useState('');
  const [nickname, setNickname] = useState('');
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [birthday, setBirthday] = useState<YearMonthDay>({ year: '', month: '', day: '' });

  const handleLoginButtonClick = () => {
    navigate('/login');
  };

  const handleOptInCheckboxClick = () => {
    console.log(isOptIn);
    setIsOptIn((prev) => !prev);
  };

  const handleEmailChange = (value: string) => {
    setEmail(value);
  };

  const handleNicknameChange = (value: string) => {
    setNickname(value);
  };

  const handleUsernameChange = (value: string) => {
    setUsername(value);
  };

  const handlePasswordChange = (value: string) => {
    setPassword(value);
  };

  const handleBirthdayChange = (value: YearMonthDay) => {
    setBirthday(value);
  };

  return (
    <S.PageContainer>
      <S.ContentWrapper>
        <S.RegisterFormContainer>
          <S.FormTitle>계정 만들기</S.FormTitle>
          <S.FormBody>
            <S.InputContainer>
              <AuthInput id="email" label="이메일" value={email} isRequired={true} handleChange={handleEmailChange} />
              <AuthInput id="nickname" label="별명" value={nickname} handleChange={handleNicknameChange} />
              <AuthInput
                id="username"
                label="사용자명"
                value={username}
                isRequired={true}
                handleChange={handleUsernameChange}
              />
              <AuthInput
                id="password"
                label="비밀번호"
                type="password"
                value={password}
                isRequired={true}
                handleChange={handlePasswordChange}
              />
              <AuthDateInput
                label="생년월일"
                isRequired={true}
                initialValue={birthday}
                handleChange={handleBirthdayChange}
              />
            </S.InputContainer>
            <S.EmailOptInContainer>
              <AuthCheckbox
                id="optIn"
                isChecked={isOptIn}
                handleChange={handleOptInCheckboxClick}
                label="(선택사항) AsyncGate 소식, 도움말, 특별 할인을 이메일로 보내주세요.
              언제든지 취소하실 수 있어요."
              />
            </S.EmailOptInContainer>
            <S.ContinueButton>
              <S.ContinueText>계속하기</S.ContinueText>
            </S.ContinueButton>
            <S.PrivacyPolicyLabel>
              등록하는 순간 AsyncGate의 <S.LinkButton>서비스 이용 약관</S.LinkButton>과
              <S.LinkButton>개인정보 보호 정책</S.LinkButton>에 동의하게 됩니다.
            </S.PrivacyPolicyLabel>
            <S.ToggleLoginButton onClick={handleLoginButtonClick}>
              <S.LoginText>이미 계정이 있으신가요?</S.LoginText>
            </S.ToggleLoginButton>
          </S.FormBody>
        </S.RegisterFormContainer>
      </S.ContentWrapper>
    </S.PageContainer>
  );
};

export default RegisterPage;
