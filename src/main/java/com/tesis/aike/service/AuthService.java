package com.tesis.aike.service;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String rawPassword);
    Map<String, Object> loginGoogleWeb(String idToken);

    Map<String, Object> loginGoogleAPK(String idToken);
}