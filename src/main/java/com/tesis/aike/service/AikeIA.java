package com.tesis.aike.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class AikeIA {

    private static final Logger logger = LoggerFactory.getLogger(AikeIA.class);
    private final ChatClient chatClient;

    // Spring inyectará automáticamente el ChatClient configurado
    // gracias a la dependencia spring-ai-openai-spring-boot-starter
    public AikeIA(ChatClient.Builder chatClientBuilder) {
        // Usar el Builder te da más flexibilidad si necesitas configuraciones específicas.
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Envía un prompt a la API de ChatGPT y devuelve la respuesta.
     *
     * @param mensaje El mensaje o pregunta para enviar a ChatGPT.
     * @return La respuesta generada por ChatGPT como un String.
     */
    public String obtenerRespuestaChat(String mensaje) {
        // Validación para evitar mensajes nulos o vacíos
        if (mensaje == null || mensaje.trim().isEmpty()) {
            return "El mensaje no puede estar vacío. Por favor, escribe algo.";
        }

        try {
            // Uso del cliente Chat para obtener la respuesta
            return chatClient.prompt()
                    .user(mensaje) // Define el mensaje del usuario
                    .call()        // Realiza la llamada a la API
                    .content();    // Extrae el contenido de la respuesta
        } catch (Exception e) {
            // Manejo de errores con registro para facilitar la depuración
            logger.error("Error al llamar a la API de OpenAI: {}", e.getMessage());
            return "Lo siento, hubo un error al procesar tu solicitud.";
        }
    }

    /**
     * Envía un prompt con un rol de sistema para guiar las respuestas de ChatGPT.
     *
     * @param mensajeSistema El mensaje del sistema que guía el contexto.
     * @param mensajeUsuario El mensaje del usuario.
     * @return La respuesta generada por ChatGPT como un String.
     */
    public String obtenerRespuestaConRolSistema(String mensajeSistema, String mensajeUsuario) {
        // Validación básica para mensajes vacíos
        if (mensajeUsuario == null || mensajeUsuario.trim().isEmpty()) {
            return "El mensaje no puede estar vacío. Por favor, escribe algo.";
        }

        try {
            return chatClient.prompt()
                    .system(mensajeSistema) // Define el comportamiento del asistente
                    .user(mensajeUsuario)   // Mensaje del usuario
                    .call()
                    .content();
        } catch (Exception e) {
            // Registro detallado del error
            logger.error("Error al usar rol de sistema con OpenAI: {}", e.getMessage());
            return "Lo siento, hubo un error al procesar tu solicitud con rol.";
        }
    }
}
