import { FriendCount } from '@/components/friend/FriendsList/styles';
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

export const DeleteFriend = async ({ friendId }: DeleteFriendParams) => {
  const { data } = await tokenAxios.delete<DeleteFriendResponse>(endPoint.friends.DELETE_FRIEND(friendId));
  return data;
};

interface GetFriendsParams {
  email: string;
}

export const GetFriends = async ({ email }: GetFriendsParams) => {
  const { data } = await tokenAxios.get<GetFriendInfoResponse>(endPoint.friends.GET_FRIENDS, { params: { email } });
  return data;
};

export const GetSentRequest = async () => {
  const { data } = await tokenAxios.get<GetSentRequestsResponse>(endPoint.friends.GET_SENT_REQUESTS);
  return data.result.friends;
};

export const GetReceivedRequest = async () => {
  const { data } = await tokenAxios.get<GetReceivedRequestsResponse>(endPoint.friends.GET_RECEIVED_REQUESTS);
  return data.result.friends;
};

export const GetFriendsList = async () => {
  const { data } = await tokenAxios.get<GetFriendsListResponse>(endPoint.friends.GET_FRIENDS_LIST);
  return data.result.friends;
};

interface PostFriendRequestParams {
  toUserId: string;
}

export const PostFriendRequest = async ({ toUserId }: PostFriendRequestParams) => {
  const { data } = await tokenAxios.post<PostRequestResponse>(endPoint.friends.POST_REQUEST(toUserId));
  return data.result;
};

interface PostRejectRequestParams {
  friendId: string;
}

export const PostRejectRequest = async ({ friendId }: PostRejectRequestParams) => {
  const { data } = await tokenAxios.post<PostRejectResponse>(endPoint.friends.POST_REJECT_REQUEST(friendId));
  return data.result;
};

interface PostAcceptRequestParams {
  friendId: string;
}

export const PostAcceptRequest = async ({ friendId }: PostAcceptRequestParams) => {
  const { data } = await tokenAxios.post<PostAcceptResponse>(endPoint.friends.POST_ACCEPT_REQUEST(friendId));
  return data.result;
};
