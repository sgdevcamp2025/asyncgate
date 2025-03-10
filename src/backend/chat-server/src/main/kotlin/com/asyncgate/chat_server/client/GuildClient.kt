package com.asyncgate.chat_server.client

import jakarta.ws.rs.core.HttpHeaders
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader

@FeignClient(name = "guild-server")
interface GuildClient {

    @GetMapping("/guilds")
    fun getGuildIds(
        @RequestHeader(HttpHeaders.AUTHORIZATION) jwtToken: String,
    ): List<String>

    @GetMapping("/direct/open/{direct-id}")
    fun getDirectDetail(
        @PathVariable("direct-id") directId: String,
    ): List<String>
}
