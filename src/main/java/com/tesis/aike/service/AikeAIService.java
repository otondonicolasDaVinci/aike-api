package com.tesis.aike.service;

import org.springframework.stereotype.Service;

@Service
public interface AikeAIService {
    String obtenerRespuestaChat(String pregunta);
}
