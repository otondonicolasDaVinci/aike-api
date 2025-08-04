package com.tesis.aike.controller;

import com.tesis.aike.model.dto.AuthRequest;
import com.tesis.aike.security.JwtTokenUtil;
import com.tesis.aike.service.AuthService;
import com.tesis.aike.service.ReservationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Map<String, Object> responseMap = authService.login(request.getUser(), request.getPassword());
            return ResponseEntity.ok(responseMap);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(Map.of("error", e.getReason()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error interno del servidor"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String bearerToken) {
        try {
            String token = bearerToken.replace("Bearer ", "");
            Claims claims = jwtTokenUtil.parse(token);

            Long userId = Long.valueOf(claims.get("s", String.class));
            if (!reservationService.hasActiveReservation(userId)) {
                return ResponseEntity.status(403).body("El usuario no tiene una reserva activa");
            }

            String role = claims.get("r", String.class);
            String newToken = jwtTokenUtil.generate(userId, role);
            return ResponseEntity.ok(Map.of("token", newToken));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido o expirado");
        }
    }

    @PostMapping("/login-google-web")
    public ResponseEntity<?> loginGoogleWeb(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            return ResponseEntity.ok(authService.loginGoogleWeb(idToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token de Google inválido"));
        }
    }

    @PostMapping("/login-google")
    public ResponseEntity<?> loginGoogleAPK(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("idToken");
            return ResponseEntity.ok(authService.loginGoogleAPK(idToken));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("error", "Token de Google inválido"));
        }
    }}


}

