package com.tesis.aike.service;

import com.tesis.aike.model.dto.PaymentRequestMercadoPagoDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;

public interface PaymentService {
    PaymentResponseMercadoPagoDTO createPayment(PaymentRequestMercadoPagoDTO request);
    void processWebhook(Long paymentId);
}
