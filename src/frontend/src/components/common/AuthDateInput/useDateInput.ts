const useDateInput = () => {
  const MAX_AGE = 150;
  const MAX_MONTH = 12;
  const MAX_DAY = 31;

  const currentYear = new Date().getFullYear();
  const years = Array.from({ length: MAX_AGE }, (_, index) => String(currentYear - index));
  const months = Array.from({ length: MAX_MONTH }, (_, index) => String(index + 1) + '월');
  const days = Array.from({ length: MAX_DAY }, (_, index) => String(index + 1));

  return { years, months, days };
};

export default useDateInput;
