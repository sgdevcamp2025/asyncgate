import { useMutation, useQueryClient } from '@tanstack/react-query';
import { useState } from 'react';

import { postAcceptRequest, postRejectRequest } from '@/api/friends';

import usePostDirect from '../../CreateDirectMessageModal/hooks/usePostDirect';

const useHandleFriendRequest = () => {
  const queryClient = useQueryClient();
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const { createDirectMessage } = usePostDirect();

  const acceptRequestMutation = useMutation({
    mutationKey: ['acceptRequest'],
    mutationFn: postAcceptRequest,
    onSuccess: (_, params) => {
      queryClient.invalidateQueries({ queryKey: ['friendsList'] });
      queryClient.invalidateQueries({ queryKey: ['receivedRequests'] });
      setErrorMessage(null);

      if (params.friendId) createDirectMessage([params.friendId]);
    },
    onError: () => {
      setErrorMessage('요청 수락에 실패했어요. 다시 시도해 주세요.');
    },
  });

  const rejectRequestMutation = useMutation({
    mutationKey: ['rejectRequest'],
    mutationFn: postRejectRequest,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['receivedRequests'] });
      setErrorMessage(null);
    },
    onError: () => {
      setErrorMessage('요청 거절에 실패했어요. 다시 시도해 주세요.');
    },
  });

  return { acceptRequestMutation, rejectRequestMutation, errorMessage };
};

export default useHandleFriendRequest;
