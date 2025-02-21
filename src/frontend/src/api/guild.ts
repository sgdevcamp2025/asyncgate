import { endPoint } from '@/constants/endPoint';
import { CreateGuildRequest, CreateGuildResponse, GetGuildsResponse } from '@/types/guilds';
import { tokenAxios } from '@/utils/axios';

export const createGuild = async (data: CreateGuildRequest) => {
  const formData = new FormData();
  formData.append('name', data.name);

  if (data.profileImage) {
    formData.append('profileImage', data.profileImage);
  }
  if (data.private) {
    formData.append('private', String(data.private));
  }

  return await tokenAxios.post<CreateGuildResponse>(endPoint.guilds.CREATE_GUILD, formData);
};

export const getGuilds = async () => {
  const { data } = await tokenAxios.get<GetGuildsResponse>(endPoint.guilds.GET_GUILDS);
  return data;
};
