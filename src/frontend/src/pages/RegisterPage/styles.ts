import { motion } from 'motion/react';
import styled, { keyframes } from 'styled-components';

import { BodyMediumText, ChipText, HeaderText, SmallText } from '@/styles/Typography';

export const PageContainer = styled.div`
  width: 100%;
  height: 100%;
`;

export const ContentWrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
`;

export const RegisterFormContainer = styled(motion.div)`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 48rem;
  height: 69.8rem;
  padding: 3.2rem;
  border-radius: 0.5rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const FormTitle = styled(HeaderText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const FormBody = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
`;

export const InputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;

  width: 100%;
  margin-top: 2rem;
`;

export const EmailOptInContainer = styled.div`
  display: flex;
  margin-top: 0.8rem;
`;

export const EmailOptInLabel = styled(SmallText)`
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const ContinueButton = styled.button`
  width: 100%;
  height: 4.4rem;
  margin-top: 2rem;
  border-radius: 0.3rem;

  background-color: ${({ theme }) => theme.colors.blue};
`;

export const ContinueText = styled(BodyMediumText)`
  color: ${({ theme }) => theme.colors.white};
`;

export const PrivacyPolicyLabel = styled(SmallText)`
  margin-top: 0.8rem;
  line-height: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const LinkButton = styled.button`
  width: fit-content;
  height: 1.6rem;
  font-size: 1.2rem;
  color: ${({ theme }) => theme.colors.link};

  &:hover {
    text-decoration: underline;
  }
`;

export const ToggleLoginButton = styled.button`
  display: flex;
  margin-top: 2rem;
`;

export const LoginText = styled(ChipText)`
  height: 1.8rem;
  color: ${({ theme }) => theme.colors.link};

  &:hover {
    text-decoration: underline;
  }
`;
