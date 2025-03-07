package com.asyncgate.state_server.controller

import com.asyncgate.state_server.service.StateService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class StateController(
    private val stateService: StateService
) {

    @GetMapping("/users-state")
    fun getUsersState(): StateResponse {
        return stateService.getUsersState()
    }
}
