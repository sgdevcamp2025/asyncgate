import { ForwardedRef, forwardRef } from 'react';

import DownArrowIcon from '@/assets/downArrow.svg';
import useDropdown from '@/hooks/useDropdown';

import * as S from './styles';
import useDropdownInput from './useDropdownInput';

export type DropdownInputItem = string;

interface DropdownInputProps {
  id: string;
  items: DropdownInputItem[];
  selectedItem: DropdownInputItem | null;
  handleSelect: (item: DropdownInputItem) => void;
  placeholder?: string;
}

const DropdownInput = (
  { id, items, selectedItem, handleSelect, placeholder }: DropdownInputProps,
  ref: ForwardedRef<HTMLInputElement>,
) => {
  const { isOpened, dropdownRef, toggleDropdown, closeDropdown } = useDropdown();
  const { selectedText, inputText, handleInputChange, handleItemClick } = useDropdownInput({
    handleSelect,
    selectedItem,
    closeDropdown,
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
    <S.DropdownContainer id={id} ref={dropdownRef}>
      <S.DropdownBody onClick={toggleDropdown}>
        {!inputText && <S.SelectedItem>{selectedText}</S.SelectedItem>}
        <S.DropdownInput
          ref={ref}
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

export default forwardRef<HTMLInputElement, DropdownInputProps>(DropdownInput);
