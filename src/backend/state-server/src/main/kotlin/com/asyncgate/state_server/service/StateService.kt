package com.asyncgate.state_server.service

import com.asyncgate.state_server.controller.StateResponse
import com.asyncgate.state_server.repository.StateRepository
import org.springframework.stereotype.Service

interface StateService {
    fun getUsersState(): StateResponse

}

@Service
class StateServiceImpl(
    private val stateRepository: StateRepository
) : StateService {
    override fun getUsersState(): StateResponse {
        return StateResponse(1L, "mock")
    }

}

