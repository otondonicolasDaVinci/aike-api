package com.tesis.aike.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AikeIA {

    private static final Logger logger = LoggerFactory.getLogger(AikeIA.class);
    private final ChatClient chatClient;

    public AikeIA(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String obtenerRespuestaChat(String mensaje) {
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return "El mensaje no puede estar vacío. Por favor, escribe algo.";
        }

        try {
            return chatClient.prompt()
                    .user(mensaje)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("Error al llamar a la API de OpenAI: {}", e.getMessage());
            return "Lo siento, hubo un error al procesar tu solicitud.";
        }
    }

    public String obtenerRespuestaConRolSistema(String mensajeSistema, String mensajeUsuario) {
        if (mensajeUsuario == null || mensajeUsuario.trim().isEmpty()) {
            return "El mensaje no puede estar vacío. Por favor, escribe algo.";
        }

        try {
            return chatClient.prompt()
                    .system(mensajeSistema)
                    .user(mensajeUsuario)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("Error al usar rol de sistema con OpenAI: {}", e.getMessage());
            return "Lo siento, hubo un error al procesar tu solicitud con rol.";
        }
    }
}
