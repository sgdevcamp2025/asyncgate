import { useQuery } from '@tanstack/react-query';

import { getFriendsList } from '@/api/friends';

const useGetFriendsList = () => {
  const result = useQuery({ queryKey: ['friendsList'], queryFn: getFriendsList, staleTime: 10 * 60 * 1000 });

  return {
    friends: result.data,
    ...result,
  };
};

export default useGetFriendsList;
