import { useQuery } from '@tanstack/react-query';

import { getSentRequest } from '@/api/friends';

const useGetSentRequests = () => {
  const result = useQuery({ queryKey: ['sentRequests'], queryFn: getSentRequest });

  return {
    sentRequests: result.data,
    ...result,
  };
};

export default useGetSentRequests;
