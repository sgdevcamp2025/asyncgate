import { endPoint } from '@/constants/endPoint';
import { GetDirectMessagesResponse, PostDirectMessageRequest } from '@/types/directMessages';
import { tokenAxios } from '@/utils/axios';

export const getDirects = async () => {
  const { data } = await tokenAxios.get<GetDirectMessagesResponse>(endPoint.directMessages.GET_DIRECTS);
  return data.result.directResponses;
};

interface PostDirectParams {
  bodyRequest: PostDirectMessageRequest;
}

export const postDirect = async ({ bodyRequest }: PostDirectParams) => {
  const { data } = await tokenAxios.post(endPoint.directMessages.POST_DIRECT, bodyRequest);
  return data.result;
};
