package com.tesis.aike.configuration;

import org.springframework.beans.factory.annotation.Value;
import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MercadoPagoConfiguration {

    public MercadoPagoConfiguration() {
    }

    @Value("${mercadopago.access-token}")
    private String accessToken;

    @Value("${mercadopago.webhook-secret}")
    private String webHookSecret;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Bean(name = "webHookSecret")
    public String getWebHookSecret() {
        return webHookSecret;
    }
}
