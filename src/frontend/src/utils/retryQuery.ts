const retryQuery = (failureCount: number, error: Error): boolean => {
  const extractedCode = error.message.match(/\b5\d{2}\b/); // 500번대 에러 검사
  if (!extractedCode) return false; // 400번대 에러의 경우 재시도하지 않음

  const statusCode = Number(extractedCode[0]);

  if (statusCode >= 500 && statusCode < 600) return failureCount < 1; // 500번대 에러는 한 번 재시도
  return false;
};

export default retryQuery;
