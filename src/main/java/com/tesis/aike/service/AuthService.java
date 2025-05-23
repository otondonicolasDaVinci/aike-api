package com.tesis.aike.service;

public interface AuthService {
    String login(Long userId, String rawPassword);
}
