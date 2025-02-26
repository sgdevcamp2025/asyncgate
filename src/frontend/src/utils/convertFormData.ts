export const convertFormData = <T extends object>(data: T): FormData => {
  const formData = new FormData();
  const keys = Object.keys(data) as Array<keyof T>;

  for (const key of keys) {
    const value = data[key];

    if (value !== undefined && value !== null) {
      if (typeof value === 'boolean') {
        formData.append(String(key), String(value));
      } else if (value instanceof File || value instanceof Blob || typeof value === 'string') {
        formData.append(String(key), value);
      } else {
        formData.append(String(key), String(value));
      }
    }
  }

  return formData;
};
