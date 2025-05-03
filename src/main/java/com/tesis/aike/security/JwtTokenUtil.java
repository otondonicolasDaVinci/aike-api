package com.tesis.aike.security;

import com.tesis.aike.helper.ConstantValues;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtTokenUtil {
    private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION = 900_000;

    public static String generate(Integer userId) {
        return Jwts.builder()
                .setSubject(userId.toString())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET)
                .compact();
    }

    public static Integer validateAndGetId(String token) {
        try {
            String sub = Jwts.parserBuilder()
                    .setSigningKey(SECRET)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return Integer.parseInt(sub);
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException(ConstantValues.Security.JWT_INVALID);
        }
    }
}
