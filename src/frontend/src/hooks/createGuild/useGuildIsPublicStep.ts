import { useState } from 'react';

const useGuildIsPublicStep = () => {
  const [isPublicGuild, setIsPublicGuild] = useState<boolean | null>(null);

  const handleVisibilityChange = (visibility: boolean) => {
    setIsPublicGuild(visibility);
  };

  return { isPublicGuild, handleVisibilityChange };
};

export default useGuildIsPublicStep;
