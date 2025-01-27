package com.asyncgate.guild_server.exception;

import lombok.Getter;

@Getter
public class GuildServerException extends RuntimeException {

    private final FailType failType;

    public GuildServerException(FailType failType) {
        super(failType.getMessage());
        this.failType = failType;
    }
}