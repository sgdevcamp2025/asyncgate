import { useNavigate } from 'react-router-dom';

import { postEmailDuplicate, postRegister } from '@/api/users';
import AuthCheckbox from '@/components/common/AuthCheckbox';
import AuthDateInput from '@/components/common/AuthDateInput';
import AuthInput from '@/components/common/AuthInput';
import useModalStore from '@/stores/modalStore';
import { formDropVarients } from '@/styles/motions';
import { PostRegisterRequest } from '@/types/users';
import parseDate from '@/utils/parseDate';

import AuthCodeModal from './components/AuthCodeModal';
import useRegister from './hooks/useRegister';
import * as S from './styles';

const RegisterPage = () => {
  const { openModal } = useModalStore();
  const navigate = useNavigate();
  const {
    userData,
    description,
    isFormValid,
    validateRequiredData,
    handleDuplicatedEmail,
    handleEmailChange,
    handleNicknameChange,
    handleUsernameChange,
    handlePasswordChange,
    handleBirthdayChange,
    handleOptInCheckboxClick,
  } = useRegister();

  const handleLoginButtonClick = () => {
    navigate('/login');
  };

  const checkEmailDuplicate = async () => {
    try {
      const response = await postEmailDuplicate({ email: userData.email });
      return response.result.is_duplicate;
    } catch (error) {
      console.error('이메일 중복 검사 실패', error);
    }
  };

  const sendAuthCode = async () => {
    try {
      const requestBody: PostRegisterRequest = {
        email: userData.email,
        nickname: userData.nickname,
        name: userData.username,
        password: userData.password,
        birth: parseDate(userData.birthday),
      };
      const response = await postRegister(requestBody);
      return response;
    } catch (error) {
      console.log('임시 회원가입 실패', error);
    }
  };

  const handleFormSubmit = async () => {
    // 폼 제출 가능 여부 확인
    if (!isFormValid) return validateRequiredData();
    // 이메일 중복 여부 확인
    const isEmailDuplicated = await checkEmailDuplicate();
    if (isEmailDuplicated) return handleDuplicatedEmail();

    // 인증번호 입력 모달
    openModal('basic', <AuthCodeModal email={userData.email} />);
    await sendAuthCode();
  };

  return (
    <S.PageContainer>
      <S.ContentWrapper>
        <S.RegisterFormContainer variants={formDropVarients} initial="hidden" animate="visible">
          <S.FormTitle>계정 만들기</S.FormTitle>
          <S.FormBody>
            <S.InputContainer>
              <AuthInput
                id="email"
                label="이메일"
                type="email"
                value={userData.email}
                description={description.email}
                isRequired={true}
                handleChange={handleEmailChange}
              />
              <AuthInput
                id="nickname"
                label="별명"
                value={userData.nickname}
                description={description.nickname}
                isRequired={true}
                handleChange={handleNicknameChange}
              />
              <AuthInput
                id="username"
                label="사용자명"
                value={userData.username}
                description={description.username}
                isRequired={true}
                handleChange={handleUsernameChange}
              />
              <AuthInput
                id="password"
                label="비밀번호"
                type="password"
                value={userData.password}
                description={description.password}
                isRequired={true}
                handleChange={handlePasswordChange}
              />
              <AuthDateInput
                label="생년월일"
                isRequired={true}
                initialValue={userData.birthday}
                description={description.birthday}
                handleChange={handleBirthdayChange}
              />
            </S.InputContainer>
            <S.EmailOptInContainer>
              <AuthCheckbox
                id="optIn"
                isChecked={userData.isOptIn}
                handleChange={handleOptInCheckboxClick}
                label="(선택사항) AsyncGate 소식, 도움말, 특별 할인을 이메일로 보내주세요.
              언제든지 취소하실 수 있어요."
              />
            </S.EmailOptInContainer>
            <S.ContinueButton type="submit" onClick={handleFormSubmit}>
              <S.ContinueText>계속하기</S.ContinueText>
            </S.ContinueButton>
            <S.PrivacyPolicyLabel>
              등록하는 순간 AsyncGate의 <S.LinkButton>서비스 이용 약관</S.LinkButton>과{' '}
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
