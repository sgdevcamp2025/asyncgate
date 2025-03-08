import { endPoint } from '@/constants/endPoint';
import {
  GetUserInfoResponse,
  PatchUserInfoRequest,
  PatchUserInfoResponse,
  PostAuthCodeRequest,
  PostAuthCodeResponse,
  PostEmailDuplicateResponse,
  PostLoginRequest,
  PostLoginResponse,
  PostRegisterRequest,
  PostRegisterResponse,
} from '@/types/users';
import { publicAxios, tokenAxios } from '@/utils/axios';

interface DeleteAccountParams {
  userId: string;
}

export const deleteAccount = async ({ userId }: DeleteAccountParams) => {
  const { data } = await tokenAxios.delete(endPoint.users.DELETE_USER, { params: userId });
  return data;
};

export const postLogin = async (requestBody: PostLoginRequest) => {
  const { data } = await publicAxios.post<PostLoginResponse>(endPoint.users.POST_SIGN_IN, requestBody);
  return data;
};

export const postRegister = async (requestBody: PostRegisterRequest) => {
  const { data } = await publicAxios.post<PostRegisterResponse>(endPoint.users.POST_SIGN_UP, requestBody);
  return data;
};

export const postAuthCode = async (requestBody: PostAuthCodeRequest) => {
  const { data } = await tokenAxios.post<PostAuthCodeResponse>(endPoint.users.POST_AUTHENTICATION_CODE, requestBody);
  return data;
};

interface GetUserIdResponse {
  httpStatus: number;
  message: string;
  time: string;
  result: string;
}

export const getUserId = async () => {
  const { data } = await tokenAxios.get<GetUserIdResponse>(endPoint.users.GET_USER_ID);
  return data.result;
};

interface PostEmailDuplicateParams {
  email: string;
}

export const postEmailDuplicate = async ({ email }: PostEmailDuplicateParams) => {
  const { data } = await publicAxios.post<PostEmailDuplicateResponse>(endPoint.users.POST_VALIDATION_EMAIL, null, {
    params: { email },
  });
  return data;
};

export const getUserInfo = async () => {
  const { data } = await tokenAxios.get<GetUserInfoResponse>(endPoint.users.GET_USER_INFO);
  return data;
};

interface PatchUserInfoParams {
  userId: string;
  bodyRequest: PatchUserInfoRequest;
}

export const patchUserInfo = async ({ userId, bodyRequest }: PatchUserInfoParams) => {
  const { data } = await publicAxios.patch<PatchUserInfoResponse>(endPoint.users.PATCH_USER_INFO, bodyRequest, {
    params: userId,
  });
  return data;
};
