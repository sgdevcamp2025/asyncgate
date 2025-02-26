import { endPoint } from '@/constants/endPoint';
import {
  CreateCategoryRequest,
  CreateCategoryResponse,
  CreateChannelRequest,
  CreateGuildRequest,
  CreateGuildResponse,
  GetGuildResponse,
  GetGuildsResponse,
} from '@/types/guilds';
import { tokenAxios } from '@/utils/axios';
import { convertFormData } from '@/utils/convertFormData';

export const createGuild = async (data: CreateGuildRequest) => {
  const formData = convertFormData(data);

  return await tokenAxios.post<CreateGuildResponse>(endPoint.guilds.CREATE_GUILD, formData, {
    headers: {
      'Content-Type': 'multipart/form-data',
    },
  });
};

export const getGuilds = async () => {
  const { data } = await tokenAxios.get<GetGuildsResponse>(endPoint.guilds.GET_GUILDS);
  return data.result.responses;
};

export const getGuild = async (guildId: string) => {
  const { data } = await tokenAxios.get<GetGuildResponse>(endPoint.guilds.GET_GUILD(guildId));
  return data.result;
};

export const createGuildCategory = async (data: CreateCategoryRequest) => {
  return await tokenAxios.post<CreateCategoryResponse>(endPoint.guilds.CREATE_CATEGORY, data);
};

export const createGuildChannel = async (data: CreateChannelRequest) => {
  return await tokenAxios.post<CreateChannelRequest>(endPoint.guilds.CREATE_CHANNEL, data);
};
