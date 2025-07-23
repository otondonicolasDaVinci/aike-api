package com.tesis.aike.controller;

import com.tesis.aike.model.dto.CartPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.service.ProductPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping("/api/product-payments")
public class ProductPaymentController {

    private final ProductPaymentService paymentService;

    @Autowired
    public ProductPaymentController(ProductPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseMercadoPagoDTO> createPayment(@RequestBody CartPaymentRequestDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody String rawBody) {
        try {
            Map<?, ?> body = new ObjectMapper().readValue(rawBody, Map.class);
            if ("payment".equalsIgnoreCase((String) body.get("type"))) {
                Long paymentId = Long.valueOf(((Map<?, ?>) body.get("data")).get("id").toString());
                paymentService.processWebhook(paymentId);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}