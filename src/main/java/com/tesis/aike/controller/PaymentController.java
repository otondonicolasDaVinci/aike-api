package com.tesis.aike.controller;

import com.tesis.aike.model.dto.PaymentRequestMercadoPagoDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> body) {
        try {
            Map<String, Object> data = (Map<String, Object>) body.get("data");
            Long paymentId = Long.valueOf(data.get("id").toString());
            paymentService.processWebhook(paymentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Webhook inválido");
        }
    }

    @PostMapping
    public ResponseEntity<PaymentResponseMercadoPagoDTO> createPayment(@RequestBody PaymentRequestMercadoPagoDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
