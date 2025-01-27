package com.asyncgate.apigatewayserver.jwt;

import com.asyncgate.apigatewayserver.exception.ApiGatewayServerException;
import com.asyncgate.apigatewayserver.exception.FailType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    public static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${jwt.secret-key}")
    private String secretKey;

    public void validate(final String token) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            String subject = claims.getBody().getSubject();
            if (StringUtils.isBlank(subject)) {
                throw new ApiGatewayServerException(FailType.JWT_INVALID_TOKEN);
            }
        } catch (SignatureException e) {
            throw new ApiGatewayServerException(FailType.JWT_INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new ApiGatewayServerException(FailType.JWT_EXPIRED_TOKEN);
        } catch (MalformedJwtException e) {
            throw new ApiGatewayServerException(FailType.JWT_MALFORMED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new ApiGatewayServerException(FailType.JWT_UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            throw new ApiGatewayServerException(FailType.JWT_INVALID_TOKEN);
        }
    }
}
