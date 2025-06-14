package com.tesis.aike.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class AikeSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtTokenUtil jwtTokenUtil) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/login",
                                "/auth/login-google"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/users",
                                "/users/refresh").permitAll()
                        .requestMatchers("/api/payments/webhook").permitAll()
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/payments/success",
                                "/api/payments/failure",
                                "/api/payments/pending",
                                "/api/qrcode/**",
                                "/cabins"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/access/check").permitAll()

                        // Nuevas reglas para /users
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/users/{id}").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers("/users/**").hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/reservations/user/{userId}").hasAnyRole("CLIENT", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/reservations").hasRole("ADMIN")

                        .anyRequest().authenticated())
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}