package com.tesis.aike.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.tesis.aike.service.GoogleTokenVerifierService;
import org.springframework.stereotype.Service;

@Service
public class GoogleTokenVerifierServiceImpl implements GoogleTokenVerifierService {

    @Override
    public FirebaseToken verify(String idTokenString) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idTokenString);
        } catch (Exception e) {
            return null;
        }
    }
}
