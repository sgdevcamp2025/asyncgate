import useUserProfileTabElements from './hooks/useUserProfileTabElements';
import * as S from './styles';
const UserProfileTab = () => {
  const { userProfileTabElements } = useUserProfileTabElements();

  return (
    <S.DropdownContainer>
      {userProfileTabElements.map((element) => (
        <S.DropdownMenu key={element.elementId} onClick={element.handleClick}>
          <S.MenuContent>{element.content}</S.MenuContent>
        </S.DropdownMenu>
      ))}
    </S.DropdownContainer>
  );
};

export default UserProfileTab;
