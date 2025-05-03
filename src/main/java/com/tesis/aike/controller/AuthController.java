package com.tesis.aike.controller;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.security.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        try {
            Integer userId = Integer.parseInt(body.get("userId"));
            String token = JwtTokenUtil.generate(userId);
            return ResponseEntity.ok(Map.of("token", token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(ConstantValues.Security.LOGIN_FAILED);
        }
    }
}
