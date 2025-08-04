package com.tesis.aike.service;

import com.google.firebase.auth.FirebaseToken;

public interface GoogleTokenVerifierService {
    FirebaseToken verify(String idTokenString);
}
