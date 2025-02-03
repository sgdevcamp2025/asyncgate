package com.asyncgate.guild_server.security.filter;

import com.asyncgate.guild_server.exception.FailType;
import com.asyncgate.guild_server.exception.GuildServerException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Collections;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    public static final String HMAC_SHA256 = "HmacSHA256";

    @Value("${jwt.secret-key}")
    private String secretKey;

    public void authenticate(final String token) {
        String userId = extract(token);
        setSecurityContext(userId, token);
    }

    private String extract(final String token) {
        try {
            Key key = new SecretKeySpec(secretKey.getBytes(), HMAC_SHA256);
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return claims.getBody().get("mid", String.class);
        } catch (Exception e) {
            throw new GuildServerException(FailType._JWT_INVALID_TOKEN);
        }
    }

    private void setSecurityContext(final String userId, final String token) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("DEFAULT_ROLE")
        );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userId, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
