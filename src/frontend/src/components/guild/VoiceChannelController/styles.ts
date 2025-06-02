import styled from 'styled-components';

import { ChipText, SmallText } from '@/styles/Typography';

export const VoiceChannelController = styled.div`
  display: flex;
  flex-direction: column;

  padding: 1rem;
  border-bottom: 1px solid ${({ theme }) => theme.colors.dark[450]};

  background-color: ${({ theme }) => theme.colors.dark[750]};
`;

export const InfoText = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ConnectStatusText = styled(ChipText)`
  font-size: 1.5rem;
  color: ${({ theme }) => theme.colors.lightGreen};
`;

export const ChannelInfoText = styled(SmallText)`
  color: ${({ theme }) => theme.colors.dark[350]};
`;

export const ConnectStatusWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;

  svg {
    cursor: pointer;
    color: ${({ theme }) => theme.colors.white};
  }
`;
