package com.asyncgate.apigatewayserver.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    public static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${jwt.secret-key}")
    private String secretKey;

    public boolean isJwtValid(final String token) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            String subject = claims.getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                log.error("JWT subject is null or empty");
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
