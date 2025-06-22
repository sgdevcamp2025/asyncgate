package com.asyncgate.user_server.security.utility;

import com.asyncgate.user_server.security.constant.Constants;
import com.asyncgate.user_server.dto.response.DefaultJsonWebTokenResponse;
import com.asyncgate.user_server.exception.FailType;
import com.asyncgate.user_server.exception.UserServerException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

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

    // token 생성 메서드
    public DefaultJsonWebTokenResponse generate(final String id, final String nickname) {
        return new DefaultJsonWebTokenResponse(
                id,
                nickname,
                generateJwt(id, accessTokenExpirePeriod)
        );
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
            throw new UserServerException(FailType._UNKNOWN_ERROR);
        }
    }

    private String generateJwt(final String identifier, final Long expirePeriod) {
        Claims claims = Jwts.claims();

        claims.put(Constants.MEMBER_ID_CLAIM_NAME, identifier);

        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setSubject(identifier)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirePeriod))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}