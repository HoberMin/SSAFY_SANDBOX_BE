package com.example.skilltestingbe.api.oauth.util;

import com.example.skilltestingbe.api.oauth.dto.LoginResponseDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private final String SECRET_KEY;
    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60;
    private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

    public JWTUtil(@Value("${SECRET.KEY}") String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    public Long getUserId(String token) {
        String id = Jwts.parser()
                .verifyWith(getSign())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        if (id == null) {
            throw new JwtException(JWTErrorCode.INVALID_TOKEN.getMessage());
        }

        return Long.parseLong(id);
    }

    public LoginResponseDto getTokens(Long userId) {
        String id = String.valueOf(userId);
        return LoginResponseDto.builder()
                .accessToken(generateToken(id, "ACCESS", ACCESS_TOKEN_EXPIRE_TIME))
                .refreshToken(generateToken(id, "REFRESH", REFRESH_TOKEN_EXPIRE_TIME))
                .build();
    }

    private String generateToken(String userId, String type, long expireTime) {
        return Jwts.builder()
                .subject(userId)
                .claim("type", type)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSign())
                .compact();
    }

    private SecretKey getSign() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String reissue(String token) {
         String id = Jwts.parser()
                .verifyWith(getSign())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

         return generateToken(id, "ACCESS", ACCESS_TOKEN_EXPIRE_TIME);
    }
}
