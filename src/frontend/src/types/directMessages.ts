interface MemberInfo {
  userId: string;
  name: string;
  nickname: string;
  profileImageUrl: string;
  email: string;
  birth: string;
}

interface DirectMessageInfo {
  directId: string;
  members: {
    responses: MemberInfo[];
  };
}

export interface GetDirectMessagesResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: {
    directResponses: DirectMessageInfo[];
  };
}

export interface PostDirectMessageRequest {
  memberIds: string[];
}

export interface PostDirectMessageResponse {
  httpStatus: number;
  message: string;
  time: Date;
  result: DirectMessageInfo;
}
