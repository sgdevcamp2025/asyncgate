import { useState } from 'react';

const useLogin = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleEmailChange = (value: string) => {
    setEmail(value);
  };

  const handlePasswordChange = (value: string) => {
    setPassword(value);
  };

  return {
    email,
    password,
    handleEmailChange,
    handlePasswordChange,
  };
};

export default useLogin;
