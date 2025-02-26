export interface CreateGuildRequest {
  name: string;
  profileImage?: File | null;
  isPrivate?: boolean;
}

export interface GuildResult {
  guildId: string;
  name: string;
  isPrivate: boolean;
  profileImageUrl: string;
}

export interface CreateGuildResponse {
  httpStatus: number;
  message: string;
  time: string;
  result: GuildResult;
}

export interface GetGuildsResponse {
  httpStatus: number;
  message: string;
  time: string;
  result: {
    responses: GuildResponse[];
  };
}

export interface GuildResponse {
  guildId: string;
  name: string;
  profileImageUrl: string;
}

export interface GetGuildResponse {
  httpStatus: number;
  message: string;
  time: string;
  result: GuildResultData;
}

export interface GuildResultData {
  guild: GuildResult;
  categories: CategoryDataResult[];
  channels: ChannelResult[];
}

export type CategoryDataResult = Omit<CategoryResult, 'guildId'>;

export interface ChannelResult {
  channelId: string;
  categoryId: string;
  name: string;
  topic: string;
  channelType: string;
  isPrivate: boolean;
}

export interface CreateCategoryResponse {
  httpsStatus: number;
  message: string;
  time: string;
  result: CategoryResult;
}

interface CategoryResult {
  categoryId: string;
  name: string;
  isPrivate: boolean;
  guildId: string;
}

export type CreateCategoryRequest = Omit<CategoryResult, 'categoryId' | 'isPrivate'> & {
  private: boolean;
};

export interface CreateChannelRequest {
  name: string;
  guildId: string;
  categoryId: string;
  private: boolean;
  channelType: ChannelType;
}

export interface CreateChannelResponse {
  httpsStatus: number;
  message: string;
  time: string;
  result: CreateChannelResponseResult;
}

interface CreateChannelResponseResult {
  channelId: string;
  name: string;
  topic: string;
  isPrivate: boolean;
  guildId: string;
  categoryId: string;
  channelType: ChannelType;
}

export type ChannelType = 'TEXT' | 'VOICE' | null;
