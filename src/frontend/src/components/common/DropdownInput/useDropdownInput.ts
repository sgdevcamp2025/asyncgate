import { useEffect, useRef, useState } from 'react';

import { DropdownInputItem } from '@/components/common/DropdownInput';

interface UseDropdownInputProps {
  handleSelect: (item: DropdownInputItem) => void;
  selectedItem: DropdownInputItem | null;
}

const useDropdownInput = ({ handleSelect, selectedItem }: UseDropdownInputProps) => {
  const [isOpened, setIsOpened] = useState(false);
  const [selectedText, setSelectedText] = useState(selectedItem);
  const [inputText, setInputText] = useState<string>('');

  const dropdownRef = useRef<HTMLDivElement>(null);

  const handleDropdownButtonClick = () => {
    setIsOpened((prev) => !prev);
  };

  const handleItemClick = (item: DropdownInputItem) => {
    handleSelect(item);
    setSelectedText(item);
    setInputText('');
    setIsOpened(false);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
      setIsOpened(false);
    }
  };

  const handleInputChange = (value: string) => {
    setInputText(value);
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [dropdownRef]);

  return {
    isOpened,
    selectedText,
    inputText,
    handleInputChange,
    handleDropdownButtonClick,
    handleItemClick,
    dropdownRef,
  };
};

export default useDropdownInput;
