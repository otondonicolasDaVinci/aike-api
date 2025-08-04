package com.tesis.aike.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.firebase.auth.FirebaseToken;

public interface GoogleTokenVerifierService {
    FirebaseToken verifyWebToken(String idTokenString);

    GoogleIdToken.Payload verifyApkToken(String idTokenString);
}
