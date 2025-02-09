import AuthCheckbox from '@/components/common/AuthCheckbox';
import AuthDateInput from '@/components/common/AuthDateInput';
import AuthInput from '@/components/common/AuthInput';

import * as S from './styles';

const RegisterPage = () => {
  return (
    <S.PageContainer>
      <S.ContentWrapper>
        <S.RegisterFormContainer>
          <S.FormTitle>계정 만들기</S.FormTitle>
          <S.FormBody>
            <S.InputContainer>
              <AuthInput id="email" label="이메일" isRequired={true} />
              <AuthInput id="nickname" label="별명" />
              <AuthInput id="username" label="사용자명" isRequired={true} />
              <AuthInput id="password" label="비밀번호" type="password" isRequired={true} />
              <AuthDateInput id="birthdate" label="생년월일" isRequired={true} />
            </S.InputContainer>
            <S.EmailOptInContainer>
              <AuthCheckbox
                id="optIn"
                isChecked={true}
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
            <S.ToggleLoginButton>
              <S.LoginText>이미 계정이 있으신가요?</S.LoginText>
            </S.ToggleLoginButton>
          </S.FormBody>
        </S.RegisterFormContainer>
      </S.ContentWrapper>
    </S.PageContainer>
  );
};

export default RegisterPage;
