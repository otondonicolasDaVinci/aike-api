package com.tesis.aike.service;

import java.util.Map;

public interface AuthService {
    Map<String, Object> login(String username, String rawPassword);
    Map<String, Object> loginGoogle(String idToken);
}