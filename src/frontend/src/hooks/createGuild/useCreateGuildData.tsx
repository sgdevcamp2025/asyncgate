import useGuildCustomizeStep from './useGuildCustomizeStep';
import useGuildIsPublicStep from './useGuildIsPublicStep';

// 길드 생성 스텝들에서 사용되는 상태 중앙 관리화를 위한 훅입니다
const useCreateGuildData = () => {
  const isPublicGuildProps = useGuildIsPublicStep();
  const guildCustomProps = useGuildCustomizeStep();

  const customPropsWithVisibility = {
    ...guildCustomProps,
    isPublicGuild: isPublicGuildProps.isPublicGuild,
  };

  return {
    isPublicGuildProps,
    guildCustomProps: customPropsWithVisibility,
  };
};

export default useCreateGuildData;
