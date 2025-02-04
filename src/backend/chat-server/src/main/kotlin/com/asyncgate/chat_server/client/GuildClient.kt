package com.asyncgate.chat_server.client

import jakarta.ws.rs.core.HttpHeaders
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

@FeignClient(name = "guild-server")
@RequestMapping("/guilds")
interface GuildClient {

    @GetMapping
    fun getGuildIds(
        @RequestHeader(HttpHeaders.AUTHORIZATION) jwtToken: String
    ): List<String>
}
