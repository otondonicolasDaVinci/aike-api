package com.tesis.aike;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.tesis.aike")
public class AikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AikeApplication.class, args);


    }
    @Bean
    public CommandLineRunner runner() {
        return args -> {
            System.out.println(">>> Aike arrancó OK.");
            System.out.println(">>> Puerto: " + System.getenv("PORT"));
        };
    }


}
