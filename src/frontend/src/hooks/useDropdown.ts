import { useEffect, useRef, useState } from 'react';

const useDropdown = () => {
  const [isOpened, setIsOpened] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  const toggleDropdown = () => {
    setIsOpened((prev) => !prev);
  };

  const closeDropdown = () => {
    setIsOpened(false);
  };

  const handleClickOutside = (event: MouseEvent) => {
    if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
      setIsOpened(false);
    }
  };

  useEffect(() => {
    document.addEventListener('mousedown', handleClickOutside);

    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return {
    isOpened,
    dropdownRef,
    toggleDropdown,
    closeDropdown,
  };
};

export default useDropdown;
