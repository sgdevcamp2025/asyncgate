import { motion } from 'motion/react';
import styled, { keyframes } from 'styled-components';

import {
  BodyMediumText,
  BodyRegularText,
  CaptionText,
  ChipText,
  HeaderText,
  MediumButtonText,
  TitleText1,
} from '@/styles/Typography';

// 공통 링크 버튼 텍스트
export const LinkText = styled(ChipText)`
  line-height: 1.6rem;
  color: ${({ theme }) => theme.colors.link};

  &:hover {
    text-decoration: underline;
  }
`;

export const PageContainer = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

export const ContentWrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

export const LoginFormContainer = styled(motion.div)`
  display: flex;
  justify-content: space-between;

  width: 78.4rem;
  height: 40.8rem;
  padding: 3.2rem;
  border-radius: 0.5rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const MainLoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 41.6rem;
  height: 34.4rem;
`;

export const LoginFormHeader = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  align-items: center;
`;

export const HeaderTitle = styled(HeaderText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const HeaderSubtitle = styled(BodyMediumText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  margin-top: 2rem;
`;

export const ForgotPasswordButton = styled.button`
  display: flex;
  justify-content: flex-start;

  width: fit-content;
  height: 1.8rem;
  margin: 0.4rem 0 2rem;
`;

export const LoginButton = styled.button`
  width: 100%;
  height: 4.4rem;
  margin-bottom: 0.8rem;
  border-radius: 0.3rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.blue};
`;

export const LoginText = styled(MediumButtonText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const ToggleRegisterContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: flex-start;
  height: 1.8rem;
`;

export const RegisterLabel = styled(CaptionText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const RegisterButton = styled.button`
  margin-left: 0.4rem;
`;

export const QRCodeLoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 24rem;
  height: 34.4rem;
`;

export const QRCodeWrapper = styled.div`
  overflow: hidden;

  width: 17.6rem;
  height: 17.6rem;
  margin-bottom: 3.2rem;
  padding: 0.8rem;
  border-radius: 0.4rem;

  background-color: ${({ theme }) => theme.colors.white};
`;

export const QRCodeImage = styled.div`
  width: 100%;
  height: 100%;
  background-image: url('/src/assets/qrcode.svg');
  background-size: cover;
`;

export const QRCodeLoginTitle = styled(TitleText1)`
  margin-bottom: 0.8rem;
  font-size: 2.4rem;
  color: ${({ theme }) => theme.colors.white};
`;

export const QRCodeLoginLabel = styled(BodyRegularText)`
  line-height: 1.25;
  color: ${({ theme }) => theme.colors.dark[300]};
  text-align: center;
`;

export const PassKeyLoginButton = styled.button`
  height: 4.4rem;
`;
