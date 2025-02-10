import { useEffect, useState } from 'react';

import { DropdownInputItem } from '@/components/common/DropdownInput';

interface UseDropdownInputProps {
  selectedItem: DropdownInputItem | null;
  handleSelect: (item: DropdownInputItem) => void;
  openDropdown: () => void;
  closeDropdown: () => void;
}

const useDropdownInput = ({ selectedItem, handleSelect, openDropdown, closeDropdown }: UseDropdownInputProps) => {
  const [selectedText, setSelectedText] = useState(selectedItem);
  const [inputText, setInputText] = useState<string>('');

  const handleItemClick = (item: DropdownInputItem) => {
    handleSelect(item);
    setSelectedText(item);
    setInputText('');
    closeDropdown();
  };

  const handleInputChange = (value: string) => {
    setInputText(value.trim());
  };

  useEffect(() => {
    if (inputText.length > 0) openDropdown();
  }, [inputText, openDropdown]);

  return {
    selectedText,
    inputText,
    handleInputChange,
    handleItemClick,
  };
};

export default useDropdownInput;
