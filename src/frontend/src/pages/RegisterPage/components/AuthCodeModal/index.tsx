import { useState } from 'react';
import { replace, useNavigate } from 'react-router-dom';

import { postAuthCode } from '@/api/users';
import Modal from '@/components/common/Modal';
import { BodyMediumText, TitleText1 } from '@/styles/Typography';

import * as S from './styles';

interface AuthCodeModalProps {
  email: string;
}

const AuthCodeModal = ({ email }: AuthCodeModalProps) => {
  const navigate = useNavigate();
  const [authCode, setAuthCode] = useState<string>('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleAuthCodeInput = (value: string) => {
    setAuthCode(value);
  };

  const handleSubmitButton = async () => {
    try {
      const response = await postAuthCode({ email, authentication_code: authCode });
      if (response.httpStatus === 200) {
        setErrorMessage('');
        return navigate('/login', { replace: true });
      }
      return setErrorMessage('인증 코드를 다시 확인해 주세요.');
    } catch (error) {
      console.error('인증 코드 확인 실패', error);
    }
  };

  return (
    <Modal name="withFooter">
      <Modal.Header>
        <TitleText1>이메일 인증 코드 입력</TitleText1>
      </Modal.Header>
      <Modal.Content>
        <S.ContentContainer>
          <BodyMediumText>{email}로 전송한 인증 코드를 입력해 주세요.</BodyMediumText>
          <S.AuthCodeInput
            maxLength={6}
            value={authCode}
            onChange={(event) => handleAuthCodeInput(event.target.value)}
          />
          <S.SubmitButton onClick={handleSubmitButton}>인증</S.SubmitButton>
          {errorMessage && <S.ErrorMessage>{errorMessage}</S.ErrorMessage>}
        </S.ContentContainer>
      </Modal.Content>
    </Modal>
  );
};

export default AuthCodeModal;
