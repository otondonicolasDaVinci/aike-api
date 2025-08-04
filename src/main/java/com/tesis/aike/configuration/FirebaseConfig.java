package com.tesis.aike.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Configuration
@Slf4j
public class FirebaseConfig {

    @PostConstruct
    public void init() {
        if (!FirebaseApp.getApps().isEmpty()) {
            return;
        }
        try {
            Resource resource = new ClassPathResource("firebase-service-account.json");
            GoogleCredentials credentials;
            if (resource.exists()) {
                try (InputStream serviceAccount = resource.getInputStream()) {
                    credentials = GoogleCredentials.fromStream(serviceAccount);
                }
            } else {
                String credentialsEnv = System.getenv("FIREBASE_CREDENTIALS");
                if (credentialsEnv != null && !credentialsEnv.isBlank()) {
                    credentials = GoogleCredentials.fromStream(
                            new ByteArrayInputStream(Base64.getDecoder().decode(credentialsEnv)));
                } else {
                    credentials = GoogleCredentials.getApplicationDefault();
                }
            }
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.warn("Skipping Firebase initialization: {}", e.getMessage());
        }
    }
}
