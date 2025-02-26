import { motion } from 'framer-motion';
import styled from 'styled-components';

export const Toggle = styled.div<{ $isOn: boolean }>`
  cursor: pointer;

  display: flex;
  align-items: center;

  width: 5rem;
  height: 2.5rem;
  padding: 0.2rem;
  border-radius: 1.2rem;

  background-color: ${({ theme, $isOn }) => ($isOn ? theme.colors.green : theme.colors.dark[300])};
`;

export const ToggleCircle = styled(motion.div)`
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.white};
`;

export const IconWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  height: 100%;

  color: ${({ theme }) => theme.colors.dark[500]};
`;
