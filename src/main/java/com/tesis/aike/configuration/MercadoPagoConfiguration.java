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
        System.out.println(">>> [MercadoPago] Init con accessToken: " + accessToken);
        MercadoPagoConfig.setAccessToken(accessToken);
        System.out.println(">>> [MercadoPago] AccessToken seteado");
    }
    @Bean(name = "webHookSecret")
    public String getWebHookSecret() {
        return webHookSecret;
    }
}
