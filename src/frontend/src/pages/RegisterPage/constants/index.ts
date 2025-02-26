import { Description, DescriptionType } from '@/components/common/AuthInput';

export const DESCRIPTION_REQUIRED: Description = {
  type: 'error',
  content: '값을 입력해 주세요.',
};

export const DESCRIPTION_EMAIL: Partial<Record<DescriptionType, Description>> = {
  error: {
    type: 'error',
    content: '이미 사용 중인 이메일이에요.',
  },
};

export const DESCRIPTION_NICKNAME: Partial<Record<DescriptionType, Description>> = {
  normal: {
    type: 'normal',
    content: '다른 회원에게 표시되는 이름이에요. 특수 문자와 이모지를 사용할 수 있어요.',
  },
};

export const DESCRIPTION_USERNAME: Partial<Record<DescriptionType, Description>> = {
  normal: {
    type: 'normal',
    content: '숫자, 영어, 밑줄 _, 마침표만 사용할 수 있어요.',
  },
  valid: {
    type: 'valid',
    content: '사용할 수 있는 사용자명이에요. 멋지네요!',
  },
  error: {
    type: 'error',
    content: '사용할 수 없는 사용자명이에요. 숫자, 문자, 밑줄_, 마침표를 추가해 보세요.',
  },
};
