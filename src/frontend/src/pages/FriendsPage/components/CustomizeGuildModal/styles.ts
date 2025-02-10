import styled from 'styled-components';

export const CustomizeGuildModal = styled.div`
  display: flex;
  background-color: pink;
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
