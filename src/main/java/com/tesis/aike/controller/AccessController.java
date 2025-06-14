package com.tesis.aike.controller;

import com.tesis.aike.service.AccessService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/access")
public class AccessController {

    private final AccessService accessService;

    public AccessController(AccessService accessService) {
        this.accessService = accessService;
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkAccess(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        boolean authorized = accessService.verifyAccessToken(token);

        if (authorized) {
            return ResponseEntity.ok("Acceso autorizado");
        } else {
            return ResponseEntity.status(403).body("Acceso denegado");
        }
    }
}
