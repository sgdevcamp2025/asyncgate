export const formDropVarients = {
  hidden: {
    opacity: 0,
    y: -100,
    z: 50,
    scale: 1.1,
  },
  visible: {
    opacity: 1,
    y: 0,
    z: 0,
    scale: 1,
    height: 'auto',
    transition: {
      duration: 0.2,
    },
  },
};

export const descriptionVarients = {
  hidden: {
    opacity: 0,
    y: 10,
  },
  visible: {
    opacity: 1,
    y: 0,
    transition: {
      duration: 0.2,
      ease: 'easeOut',
    },
  },
};
