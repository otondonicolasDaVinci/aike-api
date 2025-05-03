package com.tesis.aike.security;

import com.tesis.aike.helper.ConstantValues;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwt;

    public JwtAuthenticationFilter(JwtTokenUtil jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, jakarta.servlet.ServletException {

        String header = req.getHeader(ConstantValues.Security.AUTHORIZATION);

        if (header != null && header.startsWith(ConstantValues.Security.BEARER)) {
            try {
                String token = header.substring(ConstantValues.Security.BEARER.length());
                Claims claims = jwt.parse(token);

                String id = claims.getSubject();
                String role = claims.get("role", String.class);

                var auth = new UsernamePasswordAuthenticationToken(
                        id, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, ConstantValues.Security.JWT_INVALID);
                return;
            }
        }
        chain.doFilter(req, res);
    }
}
