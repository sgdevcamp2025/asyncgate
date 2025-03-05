import { useMutation, useQueryClient } from '@tanstack/react-query';

import { postDirect } from '@/api/directMessages';
import useModalStore from '@/stores/modalStore';

const usePostDirect = () => {
  const queryClient = useQueryClient();
  const { closeAllModal } = useModalStore();

  const createDirectMessageMutation = useMutation({
    mutationFn: postDirect,
    mutationKey: ['createDirectMessage'],
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['directMessagesList'] });
      closeAllModal();
    },
  });

  const createDirectMessage = (memberIds: string[]) => {
    createDirectMessageMutation.mutate({ bodyRequest: { memberIds } });
  };

  return { createDirectMessage, createDirectMessageMutation };
};

export default usePostDirect;
