package com.tesis.aike.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class aikeSecurity {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // Permitir POST a /api/users
                        .requestMatchers(HttpMethod.GET, "/api/users/{id}").permitAll() // Permitir POST a /api/users
                        .requestMatchers("/api/cabins").permitAll() // Permitir acceso a /api/cabins
                        .requestMatchers("/api/roles").permitAll()  // Permitir acceso a /api/roles
                        .requestMatchers("/api/notifications").permitAll()  // Permitir acceso a /api/notifications
                        .requestMatchers("/api/payments").permitAll()  // Permitir acceso a /api/payments
                        .requestMatchers("/api/reservations").permitAll()  // Permitir acceso a /api/reservations
                        .anyRequest().authenticated() // El resto requiere autenticación
                )
                .formLogin(form -> form.disable())      // Use formLogin with a lambda and disable()
                .httpBasic(basic -> basic.disable())    // Use httpBasic with a lambda and disable()
                .csrf(csrf -> csrf.disable())           // Use csrf with a lambda and disable()
                .cors(cors -> cors.disable());          // Use cors with a lambda and disable()
        return http.build();
    }
}