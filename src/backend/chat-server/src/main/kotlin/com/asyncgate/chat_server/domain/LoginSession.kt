package com.asyncgate.chat_server.domain

data class LoginSession(
    var type: Type,
    val sessionId: String,
    val userId: String,
    val communityId: String? = null,
    val ids: List<String>? = null,
) : java.io.Serializable {
    override fun toString(): String {
        return "LoginSessionRequest(type=$type, sessionId='$sessionId', userId='$userId', communityId=$communityId, ids=$ids)"
    }
}

enum class Type {
    LOGIN,
    LOGOUT,
    DIRECT,
}
