import { endPoint } from '@/constants/endPoint';
import {
  DeleteFriendResponse,
  GetFriendsListResponse,
  GetFriendInfoResponse,
  GetReceivedRequestsResponse,
  GetSentRequestsResponse,
  PostAcceptResponse,
  PostRejectResponse,
  PostRequestResponse,
} from '@/types/friends';
import { tokenAxios } from '@/utils/axios';

interface DeleteFriendParams {
  friendId: string;
}

export const deleteFriend = async ({ friendId }: DeleteFriendParams) => {
  const { data } = await tokenAxios.delete<DeleteFriendResponse>(endPoint.friends.DELETE_FRIEND(friendId));
  return data;
};

interface GetFriendInfoParams {
  email: string;
}

export const getFriendInfo = async ({ email }: GetFriendInfoParams) => {
  const { data } = await tokenAxios.get<GetFriendInfoResponse>(endPoint.friends.GET_FRIENDS, { params: { email } });
  return data.result;
};

export const getSentRequest = async () => {
  const { data } = await tokenAxios.get<GetSentRequestsResponse>(endPoint.friends.GET_SENT_REQUESTS);
  return data.result.friends;
};

export const getReceivedRequest = async () => {
  const { data } = await tokenAxios.get<GetReceivedRequestsResponse>(endPoint.friends.GET_RECEIVED_REQUESTS);
  return data.result.friends;
};

export const getFriendsList = async () => {
  const { data } = await tokenAxios.get<GetFriendsListResponse>(endPoint.friends.GET_FRIENDS_LIST);
  return data.result.friends;
};

interface PostFriendRequestParams {
  toUserId: string;
}

export const postFriendRequest = async ({ toUserId }: PostFriendRequestParams) => {
  const { data } = await tokenAxios.post<PostRequestResponse>(endPoint.friends.POST_REQUEST(toUserId));
  return data.result;
};

interface PostRejectRequestParams {
  friendId: string;
}

export const postRejectRequest = async ({ friendId }: PostRejectRequestParams) => {
  const { data } = await tokenAxios.post<PostRejectResponse>(endPoint.friends.POST_REJECT_REQUEST(friendId));
  return data.result;
};

interface PostAcceptRequestParams {
  friendId: string;
}

export const postAcceptRequest = async ({ friendId }: PostAcceptRequestParams) => {
  const { data } = await tokenAxios.post<PostAcceptResponse>(endPoint.friends.POST_ACCEPT_REQUEST(friendId));
  return data.result;
};
