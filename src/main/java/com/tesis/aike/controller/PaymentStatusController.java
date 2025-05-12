package com.tesis.aike.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentStatusController {

    @GetMapping("/success")
    public ResponseEntity<String> success() {
        return ResponseEntity.ok("Pago aprobado");
    }

    @GetMapping("/failure")
    public ResponseEntity<String> failure() {
        return ResponseEntity.ok("Pago fallido");
    }

    @GetMapping("/pending")
    public ResponseEntity<String> pending() {
        return ResponseEntity.ok("Pago pendiente");
    }
}
