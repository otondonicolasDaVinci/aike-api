package com.tesis.aike.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tesis.aike.configuration.MercadoPagoConfiguration;
import com.tesis.aike.model.dto.PaymentRequestMercadoPagoDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    private final MercadoPagoConfiguration mercadoPagoConfig;

    @Autowired
    public PaymentController(PaymentService paymentService, MercadoPagoConfiguration mercadoPagoConfig) {
        this.paymentService = paymentService;
        this.mercadoPagoConfig = mercadoPagoConfig;
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody String rawBody) {
        try {
            System.out.println("Webhook recibido con body: " + rawBody);
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
    public ResponseEntity<PaymentResponseMercadoPagoDTO> createPayment(@RequestBody PaymentRequestMercadoPagoDTO request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
