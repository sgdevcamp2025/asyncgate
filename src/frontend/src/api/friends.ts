import { FriendCount } from '@/components/friend/FriendsList/styles';
import { endPoint } from '@/constants/endPoint';
import {
  DeleteFriendResponse,
  GetFriendsListResponse,
  GetFriendsResponse,
  GetReceivedRequestsResponse,
  GetSentRequestsResponse,
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
  const { data } = await tokenAxios.get<GetFriendsResponse>(endPoint.friends.GET_FRIENDS, { params: { email } });
  return data;
};

export const GetSentRequest = async () => {
  const { data } = await tokenAxios.get<GetSentRequestsResponse>(endPoint.friends.GET_SENT_REQUESTS);
  return data;
};

export const GetReceivedRequest = async () => {
  const { data } = await tokenAxios.get<GetReceivedRequestsResponse>(endPoint.friends.GET_RECEIVED_REQUESTS);
  return data;
};

export const GetFriendsList = async () => {
  const { data } = await tokenAxios.get<GetFriendsListResponse>(endPoint.friends.GET_FRIENDS_LIST);
  return data;
};

interface PostFriendRequestParams {
  toUserId: string;
}

export const PostFriendRequest = async ({ toUserId }: PostFriendRequestParams) => {
  const { data } = await tokenAxios.post<PostFriendRequestParams>(endPoint.friends.POST_REQUEST(toUserId));
  return data;
};

interface PostRejectRequestParams {
  friendId: string;
}

export const PostRejectRequest = async ({ friendId }: PostRejectRequestParams) => {
  const { data } = await tokenAxios.post<PostRejectRequestParams>(endPoint.friends.POST_REJECT_REQUEST(friendId));
  return data;
};

interface PostAcceptRequestParams {
  friendId: string;
}

export const PostAcceptRequest = async ({ friendId }: PostAcceptRequestParams) => {
  const { data } = await tokenAxios.post<PostAcceptRequestParams>(endPoint.friends.POST_ACCEPT_REQUEST(friendId));
  return data;
};
