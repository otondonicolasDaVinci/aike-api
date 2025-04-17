package com.tesis.aike.service;

import com.tesis.aike.model.entity.CabinsEntity;
import com.tesis.aike.model.entity.ReservationsEntity; // Importar entidad de Reservas
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Necesario para SecurityContextHolder
import org.springframework.security.core.context.SecurityContextHolder; // Necesario para obtener usuario autenticado
import org.springframework.security.core.userdetails.User; // O tu clase de usuario personalizada
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.format.DateTimeFormatter; // Para formatear fechas

@Service
public class AikeIA {

    private static final Logger logger = LoggerFactory.getLogger(AikeIA.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Formato de fecha

    private final ChatClient chatClient;
    private final CabinsService cabinsService;
    private final ReservationsService reservationsService; // Añadir servicio de reservas

    // Inyección por constructor para todas las dependencias
    @Autowired
    public AikeIA(ChatClient.Builder chatClientBuilder,
                  CabinsService cabinsService,
                  ReservationsService reservationsService) { // Añadir al constructor
        this.chatClient = chatClientBuilder.build();
        this.cabinsService = cabinsService;
        this.reservationsService = reservationsService; // Asignar servicio
    }

    public String obtenerRespuestaChat(String mensaje) {
        // --- Alternativa si NO usas Spring Security ---
        // Necesitarías cambiar la firma del método, por ejemplo:
        // public String obtenerRespuestaChat(String mensaje, int userId) {
        // O pasar algún objeto User que contenga el ID.
        // ---

        if (mensaje == null || mensaje.trim().isEmpty()) {
            return "El mensaje no puede estar vacío. Por favor, escribe algo.";
        }

        String promptParaEnviar = mensaje;
        boolean consultaCabanas = detectarConsultaCabanasDisponibles(mensaje);
        boolean consultaMisReservas = detectarConsultaMisReservas(mensaje);

        // --- Lógica para Cabañas Disponibles ---
        if (consultaCabanas) {
            logger.info("Detectada pregunta sobre disponibilidad de cabañas.");
            try {
                List<CabinsEntity> cabanasDisponibles = this.cabinsService.getAvailableCabins();
                String infoCabanas;
                if (cabanasDisponibles.isEmpty()) {
                    infoCabanas = "Actualmente no hay cabañas disponibles según nuestros registros.";
                    logger.info("No se encontraron cabañas disponibles en la BD.");
                } else {
                    infoCabanas = "Según nuestros registros, las siguientes cabañas están disponibles:\n" +
                            cabanasDisponibles.stream()
                                    .map(cabana -> String.format("- Cabaña '%s' (Capacidad: %d)%s",
                                            cabana.getName(),
                                            cabana.getCapacity(),
                                            (cabana.getDescription() != null && !cabana.getDescription().trim().isEmpty()) ? ": " + cabana.getDescription() : ""
                                    ))
                                    .collect(Collectors.joining("\n"));
                    logger.info("Se encontraron {} cabañas disponibles.", cabanasDisponibles.size());
                }

                promptParaEnviar = String.format(
                        "Contexto de la base de datos sobre cabañas disponibles:\n%s\n\n" +
                                "Pregunta del usuario: \"%s\"\n\n" +
                                "Instrucción: Responde a la pregunta del usuario de forma amigable y conversacional, " +
                                "utilizando ÚNICAMENTE la información del contexto proporcionado sobre las cabañas disponibles. " +
                                "No inventes disponibilidad ni detalles adicionales.",
                        infoCabanas,
                        mensaje
                );
                logger.debug("Prompt enviado a ChatGPT con contexto de cabañas: {}", promptParaEnviar);

            } catch (Exception e) {
                logger.error("Error al obtener o formatear información de cabañas: {}", e.getMessage(), e);
                return "Lo siento, tuve problemas para consultar la disponibilidad actual de las cabañas. Por favor, intenta de nuevo más tarde.";
            }
        }
        // --- Lógica para Mis Reservas ---
        else if (consultaMisReservas) {
            logger.info("Detectada pregunta sobre las reservas del usuario.");
            try {
                // 1. Obtener el ID del usuario autenticado (¡IMPORTANTE!)
                Integer userId = 1; // Llama al nuevo método helper

                if (userId == null) {
                    logger.warn("No se pudo obtener el ID del usuario autenticado para consultar reservas.");
                    // Informar al LLM que no se pudo identificar al usuario
                    promptParaEnviar = String.format(
                            "El usuario preguntó sobre sus reservas, pero no pude identificar quién es el usuario para buscar en la base de datos. " +
                                    "Informa amablemente al usuario que necesitas saber quién es (o que necesita iniciar sesión) para poder mostrarle sus reservas. " +
                                    "Pregunta original del usuario: \"%s\"",
                            mensaje);

                } else {
                    logger.info("Consultando reservas para el usuario ID: {}", userId);
                    // 2. Consultar reservas del usuario
                    List<ReservationsEntity> misReservas = reservationsService.obtenerReservasPorUsuario(userId);

                    // 3. Formatear información
                    String infoMisReservas;
                    if (misReservas.isEmpty()) {
                        infoMisReservas = "Según nuestros registros, no tienes ninguna reserva realizada.";
                        logger.info("No se encontraron reservas para el usuario ID: {}", userId);
                    } else {
                        infoMisReservas = "Según nuestros registros, estas son tus reservas:\n" +
                                misReservas.stream()
                                        // Asumiendo que ReservationsEntity tiene estos métodos:
                                        // getId(), getCabinId() (o getCabin().getName()), getStartDate(), getEndDate(), getStatus()
                                        .map(reserva -> String.format("- Reserva ID: %d | Cabaña ID: %d | Desde: %s | Hasta: %s | Estado: %s",
                                                reserva.getId(),
                                                reserva.getCabinId(), // O: reserva.getCabin().getName() si tienes la relación cargada
                                                reserva.getStartDate().format(DATE_FORMATTER),
                                                reserva.getEndDate().format(DATE_FORMATTER),
                                                reserva.getStatus()
                                        ))
                                        .collect(Collectors.joining("\n"));
                        logger.info("Se encontraron {} reservas para el usuario ID: {}", misReservas.size(), userId);
                    }

                    // 4. Construir prompt para la IA
                    promptParaEnviar = String.format(
                            "Contexto de la base de datos sobre las reservas del usuario (ID: %d):\n%s\n\n" +
                                    "Pregunta del usuario: \"%s\"\n\n" +
                                    "Instrucción: Responde a la pregunta del usuario de forma amigable y conversacional, " +
                                    "presentando la información del contexto sobre SUS reservas. " +
                                    "Usa SÓLO esta información. No inventes reservas ni detalles.",
                            userId, // Incluir ID para posible referencia interna del LLM
                            infoMisReservas,
                            mensaje
                    );
                    logger.debug("Prompt enviado a ChatGPT con contexto de MIS RESERVAS: {}", promptParaEnviar);
                }

            } catch (Exception e) {
                logger.error("Error al obtener o formatear información de MIS RESERVAS: {}", e.getMessage(), e);
                return "Lo siento, tuve problemas para consultar tus reservas. Por favor, intenta de nuevo más tarde.";
            }
        }
        // --- Fin de la lógica ---


        // --- Llamada final a la API de ChatGPT ---
        try {
            logger.info("Enviando prompt a ChatClient...");
            return chatClient.prompt()
                    .user(promptParaEnviar) // Usamos el prompt preparado (original o modificado)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("Error al llamar a la API de Chat/OpenAI: {}", e.getMessage(), e);
            return "Lo siento, hubo un error al procesar tu solicitud con el asistente virtual.";
        }
    }

    /**
     * Método para detectar si el mensaje parece preguntar por cabañas DISPONIBLES.
     */
    private boolean detectarConsultaCabanasDisponibles(String mensaje) {
        if (mensaje == null) return false;
        String lowerCaseMensaje = mensaje.toLowerCase();
        return lowerCaseMensaje.contains("cabañas disponibles") ||
                lowerCaseMensaje.contains("disponibilidad") ||
                lowerCaseMensaje.contains("cabaña libre") ||
                lowerCaseMensaje.contains("cabañas libres") ||
                lowerCaseMensaje.contains("qué cabañas hay") ||
                lowerCaseMensaje.contains("consultar cabañas");
        // Evitar que coincida con "mis reservas" si es posible
    }

    /**
     * Método para detectar si el mensaje parece preguntar por las reservas PROPIAS del usuario.
     */
    private boolean detectarConsultaMisReservas(String mensaje) {
        if (mensaje == null) return false;
        String lowerCaseMensaje = mensaje.toLowerCase();
        // Palabras clave específicas para las reservas del usuario
        return lowerCaseMensaje.contains("mis reservas") ||
                lowerCaseMensaje.contains("ver mis reservas") ||
                lowerCaseMensaje.contains("reservas que hice") ||
                lowerCaseMensaje.contains("mis reservaciones") || // Sinónimo
                lowerCaseMensaje.contains("estado de mis reservas") ||
                (lowerCaseMensaje.contains("reservas") && (lowerCaseMensaje.contains("mias") || lowerCaseMensaje.contains("mis")));
    }

    /**
     * Obtiene el ID del usuario actualmente autenticado usando Spring Security.
     *
     * @return El ID del usuario como Integer, o null si no se puede obtener.
     * ¡IMPORTANTE! Este método asume que el 'Principal' de Spring Security
     * es un objeto User (o tu clase personalizada) y que su 'username'
     * puede ser parseado a Integer como el ID de usuario.
     * AJUSTA ESTO según cómo almacenes y representes el ID del usuario en tu sistema de autenticación.
     */
    private Integer obtenerIdUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"))) {
            Object principal = authentication.getPrincipal();
            try {
                if (principal instanceof User) { // Spring Security User class
                    // Asumiendo que el username ES el ID del usuario como String
                    return Integer.parseInt(((User) principal).getUsername());
                } else if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User) {
                    // Ejemplo si usas OAuth2/OIDC - ajusta según el atributo que contenga tu ID
                    org.springframework.security.oauth2.core.user.OAuth2User oauth2User = (org.springframework.security.oauth2.core.user.OAuth2User) principal;
                    String userIdStr = oauth2User.getAttribute("sub"); // 'sub' es común, pero podría ser otro campo
                    return userIdStr != null ? Integer.parseInt(userIdStr) : null; // O Long.parseLong si es Long
                } else if (principal instanceof String) {
                    // Si el principal es directamente el ID como String
                    return Integer.parseInt((String) principal);
                }
                // --- AÑADE AQUÍ LA LÓGICA PARA TU CLASE DE USUARIO PERSONALIZADA SI ES NECESARIO ---
                // else if (principal instanceof MiUsuarioDetails) {
                //    return ((MiUsuarioDetails) principal).getId(); // Asumiendo que tu clase tiene un getId()
                // }

                logger.warn("Tipo de Principal no reconocido o no contiene ID de usuario parseable: {}", principal.getClass().getName());
                return null; // O lanza una excepción si prefieres

            } catch (NumberFormatException e) {
                logger.error("Error al parsear el ID de usuario desde el Principal ('{}'). Asegúrate de que el Principal contenga un ID numérico.", principal, e);
                return null;
            } catch (Exception e) {
                logger.error("Error inesperado al obtener ID de usuario desde Principal: {}", e.getMessage(), e);
                return null;
            }
        }
        logger.debug("No hay usuario autenticado o es anónimo.");
        return null; // No autenticado o anónimo
    }


    // --- Método obtenerRespuestaConRolSistema sin cambios ---
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