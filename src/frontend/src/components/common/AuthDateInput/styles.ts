import styled from 'styled-components';

import { SmallText } from '@/styles/Typography';

export const DateInputContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
  width: 100%;
`;

export const Label = styled(SmallText)`
  height: 1.6rem;
  color: ${({ theme }) => theme.colors.dark[300]};
`;

export const RequiredMark = styled.span`
  width: 1.2rem;
  height: 1.2rem;
  color: ${({ theme }) => theme.colors.red};
`;

export const InputContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const YearInput = styled.div`
  width: 30%;
`;

export const MonthInput = styled.div`
  width: 35%;
`;

export const DayInput = styled.div`
  width: 30%;
`;
