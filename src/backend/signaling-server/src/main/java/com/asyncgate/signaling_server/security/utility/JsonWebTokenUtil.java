package com.asyncgate.signaling_server.security.utility;

import com.asyncgate.signaling_server.exception.FailType;
import com.asyncgate.signaling_server.exception.SignalingServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

/**
 * JWT 토큰 생성 및 검증 유틸리티 클래스
 */
@Component
public class JsonWebTokenUtil implements InitializingBean {
    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expire-period}")
    private Long accessTokenExpirePeriod;

    private Key key;

    @Override
    public void afterPropertiesSet() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // token 검증 메서드
    public Claims validate(final String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            // JWT 예외처리는 apigateway에서 처리
        } catch (Exception e) {
            throw new SignalingServerException(FailType._UNKNOWN_ERROR);
        }
    }
}