package com.tesis.aike.service;

import com.tesis.aike.model.dto.ProductPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;

public interface ProductPaymentService {
    PaymentResponseMercadoPagoDTO createPayment(ProductPaymentRequestDTO request);
    void processWebhook(Long paymentId);
}
