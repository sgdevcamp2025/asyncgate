// result가 없는 기본 응답
export interface FriendDefaultResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: null;
}

export type DeleteFriendResponse = FriendDefaultResponse;

export interface GetFriendInfoResponse {
  email: string;
  name: string;
  nickname: string;
  profileImgUrl: string;
  birth: string;
}
export interface FriendInfo {
  userId: string;
  name: string;
  nickname: string;
  profileImageUrl: string;
  email: string;
  birth: string;
}

export interface GetSentRequestsResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: {
    friends: FriendInfo[];
    status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
  };
}

export type GetReceivedRequestsResponse = GetSentRequestsResponse;

export interface GetFriendsListResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: {
    friends: FriendInfo[];
  };
}

export interface FriendRequest {
  id: string;
  userId1: string;
  userId2: string;
  requestedBy: string;
  status: 'PENDING' | 'ACCEPTED' | 'REJECTED';
}

export interface PostRequestResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: FriendRequest;
}

export type PostRejectResponse = PostRequestResponse;

export type PostAcceptResponse = PostRequestResponse;
