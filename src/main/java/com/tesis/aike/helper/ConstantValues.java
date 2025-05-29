package com.tesis.aike.helper;

public class ConstantValues {

    public static class AikeAIConstant {
        public static final String PROMPT = "prompt";
        public static final String EMPTY_PROMPT = "El prompt no puede estar vacío.";
    }

    public static class AikeAIService {
        public static final String DATE_PATTERN = "dd/MM/yyyy";
        public static final String NO_AVAILABLE_CABINS = "Actualmente no hay cabañas disponibles según nuestros registros.";
        public static final String AVAILABLE_CABINS_TEMPLATE = "Según nuestros registros, las siguientes cabañas están disponibles:\n%s";
        public static final String CONTEXT_CABINS_PROMPT = "Contexto de la base de datos sobre cabañas disponibles:\n%s\n\nPregunta del usuario: \"%s\"\n\nInstrucción: Responde a la pregunta del usuario de forma amigable y conversacional, utilizando ÚNICAMENTE la información del contexto proporcionado sobre las cabañas disponibles. No inventes disponibilidad ni detalles adicionales.";
        public static final String ERROR_CABINS = "Lo siento, tuve problemas para consultar la disponibilidad actual de las cabañas. Por favor, intenta de nuevo más tarde.";
        public static final String USER_NOT_IDENTIFIED_TEMPLATE = "El usuario preguntó sobre sus reservas, pero no pude identificar quién es el usuario para buscar en la base de datos. Informa amablemente al usuario que necesita iniciar sesión para poder mostrarle sus reservas. Pregunta original del usuario: \"%s\"";
        public static final String NO_RESERVATIONS = "Según nuestros registros, no tienes ninguna reserva realizada.";
        public static final String USER_RESERVATIONS_TEMPLATE = "Según nuestros registros, estas son tus reservas:\n%s";
        public static final String CONTEXT_RESERVATIONS_PROMPT = "Contexto de la base de datos sobre las reservas del usuario (ID: %d):\n%s\n\nPregunta del usuario: \"%s\"\n\nInstrucción: Responde a la pregunta del usuario de forma amigable y conversacional, presentando la información del contexto sobre SUS reservas. Usa SÓLO esta información. No inventes reservas ni detalles.";
        public static final String ERROR_RESERVATIONS = "Lo siento, tuve problemas para consultar tus reservas. Por favor, intenta de nuevo más tarde.";
        public static final String ERROR_OPENAI = "Lo siento, hubo un error al procesar tu solicitud con el asistente virtual.";
        public static final String RESERVATION_LINE_TEMPLATE = "- Reserva número: %d | Cabaña: %s | Desde: %s | Hasta: %s | Estado: %s";
        public static final String NOT_CONTEXT = "El usuario ha hecho una pregunta que no parece estar relacionada con la disponibilidad de cabañas o la consulta de reservas.Pregunta del usuario: \"%s\"\nInstrucción: Responde amablemente que tu función es ayudar con información sobre las \"Cabañas Aike\", como la disponibilidad y gestión de reservas.\nNo intentes responder la pregunta original del usuario. Guíalo de vuelta a los temas relevantes.";
    }

    public static class ReservationService {
        public static final String NOT_FOUND = "Reserva no encontrada";
        public static final String CABIN_NOT_AVAILABLE = "La cabaña no está disponible en las fechas solicitadas";
    }

    public static class UserService {
        public static final String NOT_FOUND = "Usuario no encontrado";
    }

    public static class Encryption {
        public static final String ERROR = "Error al encriptar la contraseña";
    }

    public static class QueryKeywords {
        public static final String[] AVAILABLE_CABINS = {
                "cabañas disponibles", "disponibilidad", "cabaña libre", "cabañas libres",
                "qué cabañas hay", "consultar cabañas", "cabañas",
                "cabañas disponibles para reservar", "cabañas disponibles para alquilar",
                "cabañas disponibles para arrendar", "cabañas disponibles para hospedarse"
        };

        public static final String[] USER_RESERVATIONS = {
                "mis reservas", "ver mis reservas", "reservas que hice", "mis reservaciones",
                "estado de mis reservas", "consultar mis reservas", "consultar mis reservaciones"
        };

        public static final String[] RESERVATION_OWNERSHIP = {"mias", "mis", "mía", "mi"};

        public static final String[] ALL_RESERVATIONS = {
                "todas las reservas", "reservas de todos", "lista completa de reservas",
                "ocupación de las cabañas", "reservas actuales", "reservas pendientes",
                "quién reservó", "reservas de las cabañas"
        };

        public static final String[] SENSITIVE_DATA = {
                "dni de", "correo de", "email de", "número de documento de"
        };
    }

    public static class Security {
        public static final String JWT_INVALID = "Token JWT inválido";
        public static final String LOGIN_FAILED = "Credenciales inválidas";
        public static final String AUTHORIZATION = "Authorization";
        public static final String BEARER = "Bearer ";
    }

    public static class LoggerMessages {
        public static final String ERROR_FETCH_CABINS = "Error al obtener información de cabañas: {}";
        public static final String ERROR_FETCH_RESERVATIONS = "Error al obtener información de reservas: {}";
        public static final String ERROR_CALL_OPENAI = "Error al llamar a la API OpenAI: {}";
        public static final String ERROR_SYSTEM_OPENAI = "Error al usar rol de sistema con OpenAI: {}";
        public static final String ERROR_AUTH_ID = "No se pudo obtener ID de usuario autenticado: {}";
    }
}
