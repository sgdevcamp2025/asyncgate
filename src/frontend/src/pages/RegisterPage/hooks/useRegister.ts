import { useState } from 'react';

import { YearMonthDay } from '@/types';

interface RegisterForm {
  email: string;
  nickname?: string;
  username: string;
  password: string;
  birthday: YearMonthDay;
  isOptIn: boolean;
}

const useRegister = () => {
  const [userData, setUserData] = useState<RegisterForm>({
    email: '',
    nickname: '',
    username: '',
    password: '',
    birthday: { year: '', month: '', day: '' },
    isOptIn: false,
  });

  const handleEmailChange = (value: string) => {
    setUserData((prev) => {
      return { ...prev, email: value };
    });
  };

  const handleNicknameChange = (value: string) => {
    setUserData((prev) => {
      return { ...prev, nickname: value };
    });
  };

  const handleUsernameChange = (value: string) => {
    setUserData((prev) => {
      return { ...prev, username: value };
    });
  };

  const handlePasswordChange = (value: string) => {
    setUserData((prev) => {
      return { ...prev, password: value };
    });
  };

  const handleBirthdayChange = (value: YearMonthDay) => {
    setUserData((prev) => {
      return { ...prev, birthday: value };
    });
  };

  const handleOptInCheckboxClick = () => {
    setUserData((prev) => {
      return { ...prev, isOptIn: !prev.isOptIn };
    });
  };

  return {
    userData,
    handleEmailChange,
    handleNicknameChange,
    handleUsernameChange,
    handlePasswordChange,
    handleBirthdayChange,
    handleOptInCheckboxClick,
  };
};

export default useRegister;
