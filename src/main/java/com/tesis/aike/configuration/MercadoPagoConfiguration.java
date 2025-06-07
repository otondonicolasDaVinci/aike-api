package com.tesis.aike.configuration;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MercadoPagoConfiguration {

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @Value("${mercadopago.webhook-secret}")
    private String webHookSecret;

    @PostConstruct
    public void init() {
        try {
            System.out.println(">>> MercadoPago init...");
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
