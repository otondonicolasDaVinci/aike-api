package com.tesis.aike.service;

import com.tesis.aike.model.dto.CartPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;

public interface ProductPaymentService {

    PaymentResponseMercadoPagoDTO createPayment(CartPaymentRequestDTO req);

    void processWebhook(Long paymentId);
}
