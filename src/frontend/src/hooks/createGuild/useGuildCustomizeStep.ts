import { useState } from 'react';

export type UseGuildNameStepType = ReturnType<typeof useGuildCustomizeStep>;

const useGuildCustomizeStep = () => {
  const [guildName, setGuildName] = useState('');
  const [profileImage, setProfileImage] = useState<File | null>(null);
  const [profileImagePreview, setProfileImagePreview] = useState<string | null>(null);

  const handleGuildNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const newName = event.target.value;

    if (newName.length !== 0) setGuildName(newName);
  };

  const handleProfileImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];

    if (file) {
      setProfileImage(file);

      const reader = new FileReader();
      reader.onload = (e) => {
        if (e.target?.result) {
          setProfileImagePreview(e.target.result as string);
        }
      };
      reader.readAsDataURL(file);
    }
  };

  return {
    guildName,
    profileImage,
    profileImagePreview,
    handleGuildNameChange,
    handleProfileImageChange,
  };
};

export default useGuildCustomizeStep;
