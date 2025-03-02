import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postAcceptRequest, postRejectRequest } from '@/api/friends';

const useHandleFriendRequest = () => {
  const queryClient = useQueryClient();

  const acceptRequestMutation = useMutation({
    mutationKey: ['acceptRequest'],
    mutationFn: postAcceptRequest,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['friendsList'] });
      queryClient.invalidateQueries({ queryKey: ['receivedRequests'] });
    },
  });

  const rejectRequestMutation = useMutation({
    mutationKey: ['rejectRequest'],
    mutationFn: postRejectRequest,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['receivedRequests'] });
    },
  });

  return { acceptRequestMutation, rejectRequestMutation };
};

export default useHandleFriendRequest;
