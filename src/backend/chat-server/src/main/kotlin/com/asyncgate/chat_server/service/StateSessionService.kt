package com.asyncgate.chat_server.service

import com.asyncgate.chat_server.domain.LoginSession
import com.asyncgate.chat_server.kafka.KafkaProperties
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

interface StateSessionService {
    fun sendLoginSessionToStateServer(loginSession: LoginSession)
}

@Service
class StateSessionServiceImpl(
    private val kafkaTemplateForLoginSession: KafkaTemplate<String, LoginSession>,
    private val kafkaProperties: KafkaProperties,
) : StateSessionService {

    override fun sendLoginSessionToStateServer(loginSession: LoginSession) {
        val sessionTopic = kafkaProperties.topic.loginSession
        val key = loginSession.sessionId
        kafkaTemplateForLoginSession.send(sessionTopic, key, loginSession)
    }
}
