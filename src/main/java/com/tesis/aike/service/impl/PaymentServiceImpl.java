package com.tesis.aike.service.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tesis.aike.model.dto.PaymentRequestMercadoPagoDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.service.PaymentService;
import com.tesis.aike.service.ReservationService;
import com.tesis.aike.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final ReservationService reservationService;
    private final UsersRepository usersRepository;

    public PaymentServiceImpl(ReservationService reservationService,
                              UsersRepository usersRepository) {
        this.reservationService = reservationService;
        this.usersRepository = usersRepository;
        MercadoPagoConfig.setAccessToken(System.getenv("MP_ACCESS_TOKEN"));
    }

    @Override
    public PaymentResponseMercadoPagoDTO createPayment(PaymentRequestMercadoPagoDTO req) {
        try {
            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title("Reserva #" + req.getReservationId())
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(req.getAmount()))
                    .build();

            String email = req.getPayerEmail();
            if (email == null || email.isBlank()) {
                var reservation = reservationService.findById(req.getReservationId().longValue());
                if (reservation != null && reservation.getUser() != null) {
                    Long uid = reservation.getUser().getId();
                    email = usersRepository.findById(uid.intValue())
                            .map(UsersEntity::getEmail)
                            .orElse(null);
                }
            }

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(PreferencePayerRequest.builder()
                            .email(email)
                            .build())
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://f847-201-216-219-13.ngrok-free.app/api/payments/success")
                            .failure("https://f847-201-216-219-13.ngrok-free.app/api/payments/failure")
                            .pending("https://f847-201-216-219-13.ngrok-free.app/api/payments/pending")
                            .build())
                    .autoReturn("approved")
                    .externalReference(req.getReservationId().toString())
                    .notificationUrl("https://f847-201-216-219-13.ngrok-free.app/api/payments/webhook")
                    .build();

            Preference preference = new PreferenceClient().create(preferenceRequest);

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
            Payment payment = new PaymentClient().get(paymentId);
            if (!"approved".equalsIgnoreCase(payment.getStatus())) return;

            Long reservationId = Long.valueOf(payment.getExternalReference());
            reservationService.updateStatus(reservationId, "PAID");

        } catch (MPApiException e) {
            if (e.getApiResponse().getStatusCode() == 404) return;
            throw new RuntimeException("MP error " + e.getApiResponse().getStatusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar webhook de Mercado Pago", e);
        }
    }
}
