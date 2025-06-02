package com.tesis.aike.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface GoogleTokenVerifierService {
    GoogleIdToken.Payload verify(String idTokenString);
}
