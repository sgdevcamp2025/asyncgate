import styled, { css } from 'styled-components';

import theme from './theme';

type TypoVariant = keyof typeof theme.typography;

const typographyStyle = (variant: TypoVariant) => css`
  font-size: ${theme.typography[variant].size};
  font-weight: ${theme.typography[variant].weight};
  line-height: ${theme.typography[variant].lineHeight};
`;

const createTypographyComponent = (htmlTag: keyof JSX.IntrinsicElements, variant: TypoVariant) => {
  return styled(htmlTag)`
    ${typographyStyle(variant)}
  `;
};

export const DisplayText = createTypographyComponent('h1', 'display');
export const HeaderText = createTypographyComponent('h2', 'header');
export const TitleText1 = createTypographyComponent('h3', 'title1');
export const TitleText2 = createTypographyComponent('h4', 'title2');
export const BodyRegularText = createTypographyComponent('p', 'bodyR');
export const BodyMediumText = createTypographyComponent('p', 'bodyM');
export const MediumButtonText = createTypographyComponent('span', 'mediumButton');
export const SmallButtonText = createTypographyComponent('span', 'smallButton');
export const CaptionText = createTypographyComponent('span', 'caption');
export const ChipText = createTypographyComponent('span', 'chip');
export const SmallText = createTypographyComponent('span', 'smallText');
