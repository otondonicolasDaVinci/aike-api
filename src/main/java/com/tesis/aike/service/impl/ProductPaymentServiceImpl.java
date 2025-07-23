package com.tesis.aike.service.impl;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.payment.PaymentItem;
import com.mercadopago.resources.preference.Preference;
import com.tesis.aike.model.dto.CartItemRequestDTO;
import com.tesis.aike.model.dto.CartPaymentRequestDTO;
import com.tesis.aike.model.dto.PaymentResponseMercadoPagoDTO;
import com.tesis.aike.model.entity.ProductEntity;
import com.tesis.aike.model.entity.UsersEntity;
import com.tesis.aike.repository.ProductRepository;
import com.tesis.aike.repository.UsersRepository;
import com.tesis.aike.service.ProductPaymentService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductPaymentServiceImpl implements ProductPaymentService {

    private static final Logger logger = LoggerFactory.getLogger(ProductPaymentServiceImpl.class);

    @Value("${mercadopago.access-token}")
    private String accessToken;

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;

    public ProductPaymentServiceImpl(ProductRepository productRepository, UsersRepository usersRepository) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
    }

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Override
    public PaymentResponseMercadoPagoDTO createPayment(CartPaymentRequestDTO req) {
        try {
            List<PreferenceItemRequest> preferenceItems = new ArrayList<>();

            for (CartItemRequestDTO itemDto : req.getItems()) {
                ProductEntity product = productRepository.findById(itemDto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemDto.getProductId()));

                int quantity = itemDto.getQuantity() == null || itemDto.getQuantity() <= 0 ? 1 : itemDto.getQuantity();

                PreferenceItemRequest item = PreferenceItemRequest.builder()
                        .id(product.getId().toString())
                        .title(product.getTitle())
                        .quantity(quantity)
                        .unitPrice(new BigDecimal(product.getPrice()))
                        .build();
                preferenceItems.add(item);
            }

            String email = req.getPayerEmail();
            if ((email == null || email.isBlank()) && req.getUserId() != null) {
                email = usersRepository.findById(req.getUserId().intValue())
                        .map(UsersEntity::getEmail)
                        .orElse(null);
            }

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(preferenceItems)
                    .payer(PreferencePayerRequest.builder().email(email).build())
                    .backUrls(PreferenceBackUrlsRequest.builder()
                            .success("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/success")
                            .failure("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/failure")
                            .pending("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/payments/pending")
                            .build())
                    .autoReturn("approved")
                    .notificationUrl("https://ymucpmxkp3.us-east-1.awsapprunner.com/api/product-payments/webhook")
                    .build();

            Preference preference = new PreferenceClient().create(preferenceRequest);

            PaymentResponseMercadoPagoDTO res = new PaymentResponseMercadoPagoDTO();
            res.setPaymentId(preference.getId());
            res.setStatus("INITIATED");
            res.setDetail(preference.getInitPoint());
            return res;

        } catch (MPApiException e) {
            String errorDetails = e.getApiResponse() != null ? e.getApiResponse().getContent() : e.getMessage();
            logger.error("Error de API de Mercado Pago: {}", errorDetails);
            throw new RuntimeException("Error de API de Mercado Pago: " + errorDetails, e);
        } catch (Exception e) {
            logger.error("Error creando preferencia de pago", e);
            throw new RuntimeException("Error creando preferencia de pago: " + e.getMessage(), e);
        }
    }

    // ... el resto de la clase (processWebhook) sigue igual ...
    @Override
    @Transactional
    public void processWebhook(Long paymentId) {
        try {
            Payment payment = new PaymentClient().get(paymentId);

            if (payment == null || !"approved".equalsIgnoreCase(payment.getStatus())) {
                return;
            }

            if (payment.getAdditionalInfo() == null || payment.getAdditionalInfo().getItems() == null) {
                return;
            }

            for (PaymentItem item : payment.getAdditionalInfo().getItems()) {
                if (item.getId() == null || item.getQuantity() == null) {
                    continue;
                }

                Long productId = Long.valueOf(item.getId());
                int quantityToReduce = item.getQuantity();

                ProductEntity product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Webhook: Producto no encontrado con id " + productId));

                if (product.getStock() != null) {
                    product.setStock(Math.max(0, product.getStock() - quantityToReduce));
                    productRepository.save(product);
                }
            }

        } catch (MPApiException e) {
            if (e.getApiResponse() != null && e.getApiResponse().getStatusCode() == 404) {
                return;
            }
            logger.error("MP API Error al procesar webhook: {}", e.getApiResponse().getContent(), e);
            throw new RuntimeException("MP API Error al procesar webhook: " + e.getApiResponse().getContent(), e);
        } catch (Exception e) {
            logger.error("Error general al procesar webhook de Mercado Pago", e);
            throw new RuntimeException("Error general al procesar webhook de Mercado Pago", e);
        }
    }
}