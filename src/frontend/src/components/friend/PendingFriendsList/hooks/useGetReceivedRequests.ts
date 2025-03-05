import { useQuery } from '@tanstack/react-query';

import { getReceivedRequest } from '@/api/friends';

const useGetReceivedRequests = () => {
  const result = useQuery({ queryKey: ['receivedRequests'], queryFn: getReceivedRequest });

  return {
    receivedRequests: result.data,
    ...result,
  };
};

export default useGetReceivedRequests;
