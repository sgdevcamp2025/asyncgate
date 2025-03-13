import styled from 'styled-components';

import { BodyMediumText } from '@/styles/Typography';

export const VideoCard = styled.div`
  position: relative;
  width: fit-content;
`;

export const Video = styled.video`
  min-width: 32rem;
  max-width: 48rem;
  border-radius: 1rem;
`;

export const UserName = styled(BodyMediumText)`
  position: absolute;
  bottom: 5%;
  left: 3%;

  width: fit-content;

  color: ${({ theme }) => theme.colors.white};
`;
