import { useQuery } from '@tanstack/react-query';

import { getSentRequest } from '@/api/friends';

const useSentRequests = () => {
  const result = useQuery({ queryKey: ['sentRequests'], queryFn: getSentRequest });

  return {
    sentRequests: result.data,
    ...result,
  };
};

export default useSentRequests;
