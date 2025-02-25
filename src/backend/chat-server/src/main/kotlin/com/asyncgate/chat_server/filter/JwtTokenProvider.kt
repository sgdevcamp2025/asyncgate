package com.asyncgate.chat_server.filter

import com.asyncgate.chat_server.exception.ChatServerException
import com.asyncgate.chat_server.exception.FailType
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SignatureException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import javax.crypto.spec.SecretKeySpec

@Component
class JwtTokenProvider {

    private val log = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    companion object {
        const val HMAC_SHA256 = "HmacSHA256"
    }

    @Value("\${jwt.secret-key}")
    private lateinit var secretKey: String

    fun validate(token: String): Boolean {
        try {
            val key: Key = SecretKeySpec(secretKey.toByteArray(), HMAC_SHA256)
            val claims: Jws<Claims> = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            val subject: String? = claims.body.subject
            if (subject.isNullOrBlank()) {
                throw ChatServerException(FailType.JWT_INVALID_TOKEN)
            }

            log.info("🔑 [JWT] Token valid. Subject: ${claims.body.subject}, Expiration: ${claims.body.expiration}")
            return true
        } catch (e: SignatureException) {
            throw ChatServerException(FailType.JWT_INVALID_SIGNATURE)
        } catch (e: ExpiredJwtException) {
            throw ChatServerException(FailType.JWT_EXPIRED_TOKEN)
        } catch (e: MalformedJwtException) {
            throw ChatServerException(FailType.JWT_MALFORMED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw ChatServerException(FailType.JWT_UNSUPPORTED_TOKEN)
        } catch (e: Exception) {
            throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        }
    }

    fun extract(token: String): String {
        try {
            val key: Key = SecretKeySpec(secretKey.toByteArray(), HMAC_SHA256)
            val claims: Jws<Claims> = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            val mid = claims.body["mid"] as? String
            if (mid.isNullOrBlank()) {
                throw ChatServerException(FailType.JWT_INVALID_TOKEN)
            }
            return mid
        } catch (e: SignatureException) {
            throw ChatServerException(FailType.JWT_INVALID_SIGNATURE)
        } catch (e: ExpiredJwtException) {
            throw ChatServerException(FailType.JWT_EXPIRED_TOKEN)
        } catch (e: MalformedJwtException) {
            throw ChatServerException(FailType.JWT_MALFORMED_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw ChatServerException(FailType.JWT_UNSUPPORTED_TOKEN)
        } catch (e: Exception) {
            throw ChatServerException(FailType.JWT_INVALID_TOKEN)
        }
    }
}
