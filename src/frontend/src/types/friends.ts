// result가 없는 기본 응답
export interface FriendDefaultResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: null;
}

export type DeleteFriendResponse = FriendDefaultResponse;

export interface GetFriendsResponse {
  email: string;
  name: string;
  nickname: string;
  profileImgUrl: string;
  birth: string;
}

export interface FriendRequest {
  id: string;
  userId1: string;
  userId2: string;
  requestedBy: string;
  status: 'PENDING' | 'ACCEPTED' | string;
}

export interface GetSentRequestsResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: {
    friends: FriendRequest[];
  };
}

export type GetReceivedRequestsResponse = GetSentRequestsResponse;

export type GetFriendsListResponse = GetSentRequestsResponse;

export interface PostFriendResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: FriendRequest;
}

export type PostRejectResponse = PostFriendResponse;

export type PostAcceptResponse = PostFriendResponse;
