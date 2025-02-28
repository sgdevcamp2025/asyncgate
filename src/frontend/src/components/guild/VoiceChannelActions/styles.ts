import styled from 'styled-components';

export const VoiceChannelActions = styled.div`
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: space-evenly;

  margin-top: 1rem;

  svg {
    color: ${({ theme }) => theme.colors.white};
  }
`;

export const Action = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 5rem;
  height: 3rem;
  border-radius: 0.8rem;

  background-color: ${({ theme }) => theme.colors.dark[500]};
`;
