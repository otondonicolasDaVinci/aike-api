package com.tesis.aike.service;

import java.util.Map;

public interface AuthService {
    String login(String userId, String rawPassword);
    Map<String, Object> loginGoogle(String idToken);

}
