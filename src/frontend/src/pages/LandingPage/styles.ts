import styled from 'styled-components';

import { BodyRegularText, DisplayText } from '@/styles/Typography';

export const PageContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 100%;

  background-image: url('/src/assets/landingBackground.png');
  background-repeat: no-repeat;
  background-position: 0 1%;
  background-size: cover;
`;

export const DescriptionContainer = styled.div`
  display: flex;
  align-items: center;

  width: fit-content;
  max-width: 120rem;
  height: 40rem;
  padding: 0 4rem;
  padding-top: 10rem;

  background-image: url('/src/assets/landingStars.webp');
  background-repeat: no-repeat;
  background-size: 90%;
`;

export const IntroContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.4rem;

  min-width: 32rem;
  max-width: 32rem;
  margin-top: -6rem;
`;

export const Title = styled(DisplayText)`
  line-height: 120%;
  color: ${({ theme }) => theme.colors.white};
`;

export const Description = styled(BodyRegularText)`
  font-size: 1.8rem;
  color: ${({ theme }) => theme.colors.white};
`;

export const PreviewImageWrapper = styled.div`
  width: 100%;
  min-width: 60rem;
  max-width: 60rem;
  min-height: 50rem;

  background-image: url('/src/assets/landingPreview.webp');
  background-repeat: no-repeat;
  background-position: 50%;
  background-size: contain;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 2.4rem;
  margin-top: 10rem;
`;

export const DownloadButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 27rem;
  height: 5.6rem;
  border-radius: 2.8rem;

  font-size: 2rem;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.black};

  background-color: ${({ theme }) => theme.colors.white};

  & > img {
    margin-right: 0.8rem;
  }
`;

export const OpenButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 34rem;
  height: 5.6rem;
  border-radius: 2.8rem;

  font-size: 2rem;
  font-weight: 500;
  color: ${({ theme }) => theme.colors.white};

  background-color: #161cbb;
`;
