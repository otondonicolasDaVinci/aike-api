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
    }

    public static class QueryKeywords {
        public static final String[] AVAILABLE_CABINS = {
                "cabañas disponibles",
                "disponibilidad",
                "cabaña libre",
                "cabañas libres",
                "qué cabañas hay",
                "consultar cabañas",
                "cabañas",
                "cabañas disponibles para reservar",
                "cabañas disponibles para alquilar",
                "cabañas disponibles para arrendar",
                "cabañas disponibles para hospedarse",
        };

        public static final String[] USER_RESERVATIONS = {
                "mis reservas",
                "ver mis reservas",
                "reservas que hice",
                "mis reservaciones",
                "estado de mis reservas",
                "reservas",
                "reservaciones",
                "consultar reservas",
                "consultar reservaciones",
                "consultar mis reservas",
                "consultar mis reservaciones",
                "consultar mis reservas",
        };

        public static final String[] RESERVATION_OWNERSHIP = { "mias", "mis", "mía", "mi" };
    }

}
