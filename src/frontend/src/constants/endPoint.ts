export const endPoint = {
  users: {
    // User API
    DELETE_USER: '/users/auth',
    PATCH_USER_INFO: '/users/info',
    PATCH_DEVICE_TOKEN: '/users/device-token',
    POST_VALIDATION_EMAIL: '/users/validation/email',
    POST_AUTHENTICATION_CODE: '/users/validation/authentication',
    POST_SIGN_UP: '/users/sign-up',
    POST_SIGN_IN: '/users/sign-in',
  },

  guilds: {
    // Guild Category API
    DELETE_CATEGORY: (guildId: string, categoryId: string) => `/guilds/category/${guildId}/${categoryId}`,
    CREATE_CATEGORY: '/guilds/category',

    // Guild Channel API
    DELETE_CHANNEL: (guildId: string, categoryId: string, channelId: string) =>
      `/guilds/channel/${guildId}/${categoryId}/${channelId}`,
    PATCH_CHANNEL: (guildId: string, categoryId: string, channelId: string) =>
      `/guilds/channel/${guildId}/${categoryId}/${channelId}`,
    CREATE_CHANNEL: '/guilds/channel',

    // Guilds API
    DELETE_GUILD: (guildId: string) => `/guilds/guilds/${guildId}`,
    GET_GUILDS: '/guilds/guilds',
    GET_GUILD: (guildId: string) => `/guilds/guilds/${guildId}`,
    GET_RANDOM_GUILDS: '/guilds/guilds/rand',
    PATCH_GUILD_INFO: (guildId: string) => `/guilds/guilds/${guildId}`,
    CREATE_GUILD: '/guilds/guilds',

    // Guild Member API
    CANCEL_GUILD_INVITATION: (guildId: string) => `/guilds/guilds/${guildId}/invitations`,
    REJECT_GUILD_INVITATION: (guildId: string) => `/guilds/guilds/${guildId}/invitations/reject`,
    ACCEPT_GUILD_INVITATION: (guildId: string) => `/guilds/guilds/${guildId}/invitations/accept`,
    SEND_INVITATION: (guildId: string) => `/guilds/guilds/${guildId}/invitations`,
  },
};
