package com.tesis.aike.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Key key;

    public Key getKey() {
        return key;
    }

    public JwtTokenUtil(@Value("${jwt.secret.key}") String secret) { //TODO: Configurar en variables de entorno nombre JWT_SECRET_KEY valor: 0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF0123456789ABCDEF.
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generate(Long userId, String role) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claim("s", userId.toString())
                .claim("r", role)
                .setExpiration(Date.from(now.plus(10, ChronoUnit.MINUTES)))
                .signWith(key)
                .compact();
    }


    public Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
