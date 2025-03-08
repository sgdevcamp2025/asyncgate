export const endPoint = {
  users: {
    // User API
    DELETE_USER: '/users/auth',
    GET_USER_INFO: '/users/info',
    PATCH_USER_INFO: '/users/info',
    PATCH_DEVICE_TOKEN: '/users/device-token',

    // Login, Registration API
    POST_VALIDATION_EMAIL: '/users/validation/email',
    POST_AUTHENTICATION_CODE: '/users/validation/authentication-code',
    POST_SIGN_UP: '/users/sign-up',
    POST_SIGN_IN: '/users/sign-in',
    GET_USER_ID: '/users/user/id',
  },

  friends: {
    // Friends API
    DELETE_FRIEND: (friendId: string) => `/users/friends/${friendId}`,
    GET_FRIENDS: '/users/friends',
    GET_FRIENDS_LIST: '/users/friends/list',

    // Friends Request API
    GET_SENT_REQUESTS: '/users/friends/sent',
    GET_RECEIVED_REQUESTS: '/users/friends/received',
    POST_REQUEST: (toUserId: string) => `/users/friends/request/${toUserId}`,
    POST_REJECT_REQUEST: (friendId: string) => `/users/friends/reject/${friendId}`,
    POST_ACCEPT_REQUEST: (friendId: string) => `/users/friends/accept/${friendId}`,
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

  directMessages: {
    // Direct Message API
    GET_DIRECTS: '/guilds/direct',
    POST_DIRECT: '/guilds/direct',
  },
};
