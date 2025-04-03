package com.tesis.aike.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt; // Opcional si usas la forma básica
import org.springframework.stereotype.Service;

@Service
public class AikeIA {

    private final ChatClient chatClient;

    // Spring inyectará automáticamente el ChatClient configurado
    // gracias a la dependencia spring-ai-openai-spring-boot-starter
    // y la configuración de la API Key.
    public AikeIA(ChatClient.Builder chatClientBuilder) {
        // Usar el Builder te da más flexibilidad si necesitas configuraciones
        // específicas por cliente, aunque para un uso básico, inyectar
        // ChatClient directamente también suele funcionar si solo hay un proveedor.
        this.chatClient = chatClientBuilder.build();
    }

    /**
     * Envía un prompt a la API de ChatGPT y devuelve la respuesta.
     *
     * @param mensaje El mensaje o pregunta para enviar a ChatGPT.
     * @return La respuesta generada por ChatGPT como un String.
     */
    public String obtenerRespuestaChat(String mensaje) {
        try {
            // Forma recomendada usando la API fluida de Prompt
            return chatClient.prompt()
                    .user(mensaje) // Define el mensaje del usuario
                    .call()        // Realiza la llamada a la API
                    .content();    // Extrae el contenido de la respuesta

            /* Forma alternativa (más explícita):
            Prompt prompt = new Prompt(mensaje);
            ChatResponse respuesta = chatClient.call(prompt);
            return respuesta.getResult().getOutput().getContent();
            */

        } catch (Exception e) {
            // Es importante manejar posibles errores de la API (ej: clave inválida,
            // problemas de red, límites de tasa, etc.)
            System.err.println("Error al llamar a la API de OpenAI: " + e.getMessage());
            // Considera usar un logger en lugar de System.err
            // Log.error("Error al llamar a la API de OpenAI", e);
            return "Lo siento, hubo un error al procesar tu solicitud.";
        }
    }

    // Puedes añadir más métodos para interacciones más complejas,
    // como mantener historial de conversación, definir roles (system, user, assistant), etc.
    public String obtenerRespuestaConRolSistema(String mensajeSistema, String mensajeUsuario) {
        try {
            return chatClient.prompt()
                    .system(mensajeSistema) // Define el comportamiento del asistente
                    .user(mensajeUsuario)
                    .call()
                    .content();
        } catch (Exception e) {
            System.err.println("Error al llamar a la API de OpenAI con rol de sistema: " + e.getMessage());
            return "Lo siento, hubo un error al procesar tu solicitud con rol.";
        }
    }
}