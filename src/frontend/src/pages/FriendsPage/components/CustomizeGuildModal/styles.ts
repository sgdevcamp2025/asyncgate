import styled from 'styled-components';

import { CaptionText } from '../../../../styles/Typography';

export const CustomizeGuildModal = styled.div`
  display: flex;
`;

export const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  align-items: center;
`;

export const ImageUpLoad = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 8rem;
  height: 8rem;
  border: 1px dashed ${({ theme }) => theme.colors.dark[400]};
  border-radius: 100%;
`;

export const UpLoadIcon = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const PlusIcon = styled.div`
  position: absolute;
  top: -0.2rem;
  right: -0.2rem;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 2.4rem;
  height: 2.4rem;
  border-radius: 100%;

  background-color: ${({ theme }) => theme.colors.blue};
`;
export const GuildNameWrapper = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
  align-items: start;

  width: 100%;
`;

export const GuildNameInput = styled.input`
  display: flex;
  align-items: center;

  width: 100%;
  height: 3rem;
  padding: 0.4rem 0 0.4rem 0.8rem;
  border: none;
  border-radius: 0.4rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.dark[700]};
  outline: none;
`;

export const Caption = styled(CaptionText)`
  color: ${({ theme }) => theme.colors.dark[400]};
`;

export const FooterContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 5.4rem;
  padding: 0 2rem;

  background-color: ${({ theme }) => theme.colors.dark[600]};
`;

export const BackButton = styled.button`
  color: ${({ theme }) => theme.colors.white};
  background-color: transparent;
`;

export const CreateButton = styled.button`
  width: 11rem;
  height: 4rem;
  border-radius: 0.8rem;

  color: ${({ theme }) => theme.colors.white};

  background-color: ${({ theme }) => theme.colors.blue};
`;
