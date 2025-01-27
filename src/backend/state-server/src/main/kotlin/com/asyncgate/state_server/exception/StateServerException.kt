package com.asyncgate.state_server.exception

class StateServerException(
    val failType: FailType,
) : RuntimeException(failType.message)
