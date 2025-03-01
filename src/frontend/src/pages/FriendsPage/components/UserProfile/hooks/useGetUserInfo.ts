import { useQuery } from '@tanstack/react-query';
import { useEffect } from 'react';

import { getUserInfo } from '@/api/users';
import { useUserInfoStore } from '@/stores/userInfo';

const useGetUserInfo = () => {
  const { setUserInfo, clearUserInfo } = useUserInfoStore();

  const queryResult = useQuery({
    queryKey: ['userInfo'],
    queryFn: getUserInfo,
    staleTime: 14 * 24 * 60 * 60 * 1000,
  });

  useEffect(() => {
    if (queryResult.data) setUserInfo({ userId: queryResult.data.result.userId });
  }, [queryResult.data, setUserInfo]);

  useEffect(() => {
    if (queryResult.isError) clearUserInfo();
  }, [queryResult.isError, clearUserInfo]);

  return { userInfo: queryResult.data?.result, ...queryResult };
};

export default useGetUserInfo;
