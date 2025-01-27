package com.asyncgate.state_server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class StateServerApplication

fun main(args: Array<String>) {
	runApplication<StateServerApplication>(*args)
}
