package com.tesis.aike.controller;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AuthService;
import com.tesis.aike.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ReservationService reservationService;

    @Autowired
    public AuthController(AuthService authService,
                          JwtTokenUtil jwtTokenUtil,
                          ReservationService reservationService) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.reservationService = reservationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            String user = body.get("user") != null ? body.get("user") : null;
            String pwd = body.get("password");
            String token = authService.login(user, pwd);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(ConstantValues.Security.LOGIN_FAILED);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.replace("Bearer ", "");
            Claims claims = jwtTokenUtil.parse(token);

            Long userId = Long.valueOf(claims.getSubject());
            if (!reservationService.hasActiveReservation(userId)) {
                return ResponseEntity.status(403).body("El usuario no tiene una reserva activa");
            }

            String role = claims.get("role", String.class);
            String newToken = jwtTokenUtil.generate(userId, role);
            return ResponseEntity.ok(newToken);

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }
    }

    @PostMapping("/login-google")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            return ResponseEntity.ok(authService.loginGoogle(idToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token de Google inválido");
        }
    }

}
