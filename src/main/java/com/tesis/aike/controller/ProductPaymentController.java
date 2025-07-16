package com.tesis.aike.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesis.aike.model.dto.ProductPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.service.ProductPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/product-payments")
public class ProductPaymentController {

    private final ProductPaymentService paymentService;

    @Autowired
    public ProductPaymentController(ProductPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody String rawBody) {
        try {
            Map<?, ?> body = new ObjectMapper().readValue(rawBody, Map.class);
            Long paymentId = Long.valueOf(((Map<?, ?>) body.get("data")).get("id").toString());
            paymentService.processWebhook(paymentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping
    public ResponseEntity<PaymentResponseMercadoPagoDTO> createPayment(@RequestBody ProductPaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
