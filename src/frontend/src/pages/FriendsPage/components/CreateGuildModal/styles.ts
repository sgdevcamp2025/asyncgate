import styled from 'styled-components';

export const CreateGuildModal = styled.div`
  display: flex;
  border: 1px solid red;
`;

export const CreateButtons = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.6rem;
  padding: 3.6rem 4.6rem 0;
`;

export const CreatePrivateGuild = styled.button`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0.9rem 2rem;
  border: 1px solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 0.8rem;

  color: ${({ theme }) => theme.colors.white};
`;

export const CreatePublicGuild = styled.button`
  display: flex;
  align-items: center;
  justify-content: space-between;

  padding: 0.9rem 2rem;
  border: 1px solid ${({ theme }) => theme.colors.dark[400]};
  border-radius: 0.8rem;

  color: ${({ theme }) => theme.colors.white};
`;
