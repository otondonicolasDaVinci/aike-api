package com.tesis.aike.configuration;

import org.springframework.beans.factory.annotation.Value;
import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    public MercadoPagoConfiguration() {
        System.out.println(">>> Constructor de MercadoPagoConfiguration");
    }

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @Value("${mercadopago.webhook-secret}")
    private String webHookSecret;

    @PostConstruct
    public void init() {
        try {
            System.out.println(">>> MercadoPago init...");
            System.out.println(">>> accessToken: " + accessToken);
            MercadoPagoConfig.setAccessToken(accessToken);
            System.out.println(">>> MercadoPago OK");
        } catch (Exception e) {
            System.err.println(">>> Error en MercadoPago init: " + e.getMessage());
        }
    }

    @Bean(name = "webHookSecret")
    public String getWebHookSecret() {
        return webHookSecret;
    }
}
