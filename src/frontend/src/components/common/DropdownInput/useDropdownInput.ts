import { useState } from 'react';

import { DropdownInputItem } from '@/components/common/DropdownInput';

interface UseDropdownInputProps {
  selectedItem: DropdownInputItem | null;
  handleSelect: (item: DropdownInputItem) => void;
  closeDropdown: () => void;
}

const useDropdownInput = ({ selectedItem, handleSelect, closeDropdown }: UseDropdownInputProps) => {
  const [selectedText, setSelectedText] = useState(selectedItem);
  const [inputText, setInputText] = useState<string>('');

  const handleItemClick = (item: DropdownInputItem) => {
    handleSelect(item);
    setSelectedText(item);
    setInputText('');
    closeDropdown();
  };

  const handleInputChange = (value: string) => {
    setInputText(value);
  };

  return {
    selectedText,
    inputText,
    handleInputChange,
    handleItemClick,
  };
};

export default useDropdownInput;
