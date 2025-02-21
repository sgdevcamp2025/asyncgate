export interface CreateGuildRequest {
  name: string;
  profileImage?: string;
  private?: boolean;
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
