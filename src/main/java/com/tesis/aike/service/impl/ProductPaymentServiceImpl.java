package com.tesis.aike.service.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferencePayerRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import com.tesis.aike.model.dto.ProductPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.model.entity.ProductEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.ProductRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.service.ProductPaymentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductPaymentServiceImpl implements ProductPaymentService {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    public ProductPaymentServiceImpl(ProductRepository productRepository,
                                     UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        MercadoPagoConfig.setAccessToken(System.getenv("MP_ACCESS_TOKEN"));
    }

    @Override
    public PaymentResponseMercadoPagoDTO createPayment(ProductPaymentRequestDTO req) {
        try {
            ProductEntity product = productRepository.findById(req.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            int quantity = req.getQuantity() == null || req.getQuantity() <= 0 ? 1 : req.getQuantity();

            PreferenceItemRequest item = PreferenceItemRequest.builder()
                    .title(product.getTitle())
                    .quantity(quantity)
                    .unitPrice(BigDecimal.valueOf(product.getPrice()))
                    .build();

            String email = req.getPayerEmail();
            if ((email == null || email.isBlank()) && req.getUserId() != null) {
                email = usersRepository.findById(req.getUserId().intValue())
                        .map(UsersEntity::getEmail)
                        .orElse(null);
            }

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(List.of(item))
                    .payer(PreferencePayerRequest.builder()
                            .email(email)
                            .build())
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/success")
                            .failure("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/failure")
                            .pending("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/pending")
                            .build())
                    .autoReturn("approved")
                    .externalReference("product-" + product.getId() + "-" + quantity)
                    .notificationUrl("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/product-payments/webhook")
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

            String reference = payment.getExternalReference();
            if (reference == null || !reference.startsWith("product-")) return;
            String[] refParts = reference.replace("product-", "").split("-");
            Long productId = Long.valueOf(refParts[0]);
            int quantity = 1;
            if (refParts.length > 1) {
                try {
                    quantity = Integer.parseInt(refParts[1]);
                } catch (NumberFormatException ignore) {}
            }

            try {
                if (payment.getAdditionalInfo() != null &&
                        payment.getAdditionalInfo().getItems() != null &&
                        !payment.getAdditionalInfo().getItems().isEmpty() &&
                        payment.getAdditionalInfo().getItems().get(0).getQuantity() != null) {
                    quantity = payment.getAdditionalInfo().getItems().get(0).getQuantity();
                }
            } catch (Exception ignored) {}

            int finalQuantity1 = quantity;
            productRepository.findById(productId).ifPresent(p -> {
                final int finalQuantity = finalQuantity1; // Declarar una variable final
                if (p.getStock() != null) {
                    p.setStock(Math.max(0, p.getStock() - finalQuantity));
                    productRepository.save(p);
                }
            });
        } catch (MPApiException e) {
            if (e.getApiResponse().getStatusCode() == 404) return;
            throw new RuntimeException("MP error " + e.getApiResponse().getStatusCode(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar webhook de Mercado Pago", e);
        }
    }
}
