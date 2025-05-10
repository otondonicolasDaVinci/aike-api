package com.tesis.aike.service.impl;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tesis.aike.model.dto.PaymentRequestMercadoPagoDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.model.dto.ReservationDTO;
import com.tesis.aike.service.PaymentService;
import com.tesis.aike.service.ReservationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final ReservationService reservationService;

    public PaymentServiceImpl(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Override
    public PaymentResponseMercadoPagoDTO createPayment(PaymentRequestMercadoPagoDTO req) {
        try {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Reserva #" + req.getReservationId())
                    .quantity(1)
                    .unitPrice(new BigDecimal(req.getAmount().toString()))
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(PreferencePayerRequest.builder()
                            .email(req.getPayerEmail())
                            .build())
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://example.com/success")
                            .failure("https://example.com/failure")
                            .pending("https://example.com/pending")
                            .build())
                    .autoReturn("approved")
                    .externalReference(req.getReservationId().toString())
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            PaymentResponseMercadoPagoDTO res = new PaymentResponseMercadoPagoDTO();
            res.setPaymentId(preference.getId());
            res.setStatus("INITIATED");
            res.setDetail(preference.getInitPoint());
            return res;

        } catch (Exception e) {
            throw new RuntimeException("Error creando preferencia de pago: " + e.getMessage(), e);
        }
    }

    @Override
    public void processWebhook(Long paymentId) {
        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(paymentId);

            if (!"approved".equalsIgnoreCase(payment.getStatus())) return;

            String externalRef = payment.getExternalReference();
            Long reservationId = Long.valueOf(externalRef);

            ReservationDTO dto = reservationService.findById(reservationId);
            dto.setStatus("PAID");
            reservationService.update(reservationId, dto);

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar webhook de Mercado Pago", e);
        }
    }
}
