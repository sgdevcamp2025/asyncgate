import DownArrowIcon from '@/assets/downArrow.svg';

import * as S from './styles';
import useDropdownInput from './useDropdownInput';

export type DropdownInputItem = string;

interface DropdownProps {
  items: DropdownInputItem[];
  selectedItem: DropdownInputItem | null;
  handleSelect: (item: DropdownInputItem) => void;
  placeholder?: string;
}

const DropdownInput = ({ items, selectedItem, handleSelect, placeholder }: DropdownProps) => {
  const {
    isOpened,
    selectedText,
    inputText,
    handleInputChange,
    handleDropdownButtonClick,
    handleItemClick,
    dropdownRef,
  } = useDropdownInput({
    handleSelect,
    selectedItem,
  });

  const filteredItems = items.filter((item) => item.includes(inputText));

  const renderItems = () => {
    if (filteredItems.length === 0) return <S.EmptyItem>검색 결과가 없어요</S.EmptyItem>;

    return filteredItems.map((item) => {
      return (
        <S.DropdownItem key={item} $isSelected={item === selectedItem} onClick={() => handleItemClick(item)}>
          {item}
        </S.DropdownItem>
      );
    });
  };

  return (
    <S.DropdownContainer ref={dropdownRef}>
      <S.DropdownBody onClick={handleDropdownButtonClick}>
        {!inputText && <S.SelectedItem>{selectedText}</S.SelectedItem>}
        <S.DropdownInput
          value={inputText}
          onChange={(event) => handleInputChange(event.target.value)}
          placeholder={selectedText ? '' : placeholder}
        />
        <S.ArrowIcon src={DownArrowIcon} alt="" />
      </S.DropdownBody>
      {isOpened && <S.ItemContainer>{renderItems()}</S.ItemContainer>}
    </S.DropdownContainer>
  );
};

export default DropdownInput;
