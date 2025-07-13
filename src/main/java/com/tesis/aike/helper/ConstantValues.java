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
        public static final String FALLBACK_GUIDANCE_PROMPT = """
                El usuario ha hecho una pregunta que no parece estar relacionada con la disponibilidad de cabañas o la consulta de reservas.
                Pregunta del usuario: "%s"

                Instrucción: Responde amablemente que tu función es ayudar con información sobre las "Cabañas Aike", como la disponibilidad y gestión de reservas.
                No intentes responder la pregunta original del usuario. Guíalo de vuelta a los temas relevantes.
                """;

        // Información de productos
        public static final String PRODUCTS_TEMPLATE = "Estos son los productos disponibles en la tienda:\n%s";
        public static final String CONTEXT_PRODUCTS_PROMPT = "Contexto sobre los productos disponibles:\n%s\n\nPregunta del usuario: \"%s\"\n\nInstrucción: Responde utilizando únicamente la información del contexto.";
        public static final String NO_PRODUCTS = "Actualmente no hay productos disponibles en la tienda.";
        public static final String ERROR_PRODUCTS = "Lo siento, hubo un problema al obtener los productos.";

        // Información de la cabaña reservada
        public static final String USER_CABIN_TEMPLATE = "Información de tu cabaña:\nNombre: %s\nCapacidad: %d\nDescripción: %s";
        public static final String CONTEXT_CABIN_PROMPT = "Contexto de la base de datos sobre la cabaña reservada por el usuario (ID reserva: %d):\n%s\n\nPregunta del usuario: \"%s\"\n\nInstrucción: Responde utilizando sólo la información del contexto.";
        public static final String USER_NO_RESERVATION = "Según nuestros registros, no tienes una cabaña reservada.";
        public static final String ERROR_CABIN_INFO = "Lo siento, hubo un problema al consultar la información de tu cabaña.";

        // Información de recorridos turísticos
        public static final String TOURS_TEMPLATE = "Estos son los recorridos recomendados para la cabaña %s:\n%s";
        public static final String CONTEXT_TOURS_PROMPT = "Contexto sobre recorridos turísticos:\n%s\n\nPregunta del usuario: \"%s\"\n\nInstrucción: Responde utilizando sólo la información del contexto.";
        public static final String NO_TOURS = "No hay recorridos turísticos registrados para tu cabaña.";
        public static final String ERROR_TOURS = "Lo siento, hubo un problema al obtener los recorridos.";
    }

    public static class ReservationService {
        public static final String NOT_FOUND = "Reserva no encontrada";
        public static final String CABIN_NOT_AVAILABLE = "La cabaña no está disponible en las fechas solicitadas";
        public static final String INVALID_DATA = "Datos de reserva inválidos";
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
            "estado de mis reservas", "consultar mis reservas", "consultar mis reservaciones",
            "informacion de mi reserva", "detalles de mi reserva", "mi reserva", "reservas que tengo", "reserva", "consultar mi reserva",
            "cual es mi reserva", "informacion sobre mi reserva", "ver mi reserva", "ver mis reservaciones",
            "mis reservas actuales", "mis reservas pasadas", "mis reservas pendientes", "mis reservas confirmadas",
            "quiero ver mis reservas", "quiero consultar mis reservas", "quiero saber mis reservas",
            "reservas personales", "reservas propias", "mis reservas de cabañas", "mis reservas en cabañas",
            "reservas que tengo hechas", "reservas que tengo confirmadas", "reservas que tengo pendientes",
            "mis reservas activas", "mis reservas canceladas", "mis reservas completadas",
            "quiero ver las reservas que hice", "quiero ver las reservas que tengo", "quiero ver las reservas que están activas",
            "quiero ver las reservas que están pendientes", "quiero ver las reservas que están confirmadas",
            "quiero ver las reservas que están canceladas", "quiero ver las reservas que están completadas",
            "quiero consultar las reservas que hice", "quiero consultar las reservas que tengo",
            "quiero consultar las reservas que están activas", "quiero consultar las reservas que están pendientes",
            "quiero consultar las reservas que están confirmadas", "quiero consultar las reservas que están canceladas",
            "quiero consultar las reservas que están completadas", "quiero saber las reservas que hice",
            "quiero saber las reservas que tengo", "quiero saber las reservas que están activas",
            "quiero saber las reservas que están pendientes", "quiero saber las reservas que están confirmadas",
            "quiero saber las reservas que están canceladas", "quiero saber las reservas que están completadas",
            "quiero información sobre mis reservas", "quiero detalles sobre mis reservas",
            "quiero información sobre las reservas que hice", "quiero información sobre las reservas que tengo",
            "quiero información sobre las reservas que están activas", "quiero información sobre las reservas que están pendientes",
            "quiero información sobre las reservas que están confirmadas", "quiero información sobre las reservas que están canceladas",
            "quiero información sobre las reservas que están completadas", "quiero detalles sobre las reservas que hice",
            "quiero detalles sobre las reservas que tengo", "quiero detalles sobre las reservas que están activas",
            "quiero detalles sobre las reservas que están pendientes", "quiero detalles sobre las reservas que están confirmadas",
            "quiero detalles sobre las reservas que están canceladas", "quiero detalles sobre las reservas que están completadas"
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

        public static final String[] PRODUCTS = {"productos", "tienda", "que venden", "comprar"};

        public static final String[] MY_CABIN = {"mi cabaña", "cabaña que reservé", "información de la cabaña"};

        public static final String[] TOURS = {"recorridos", "tours", "actividades", "atracciones"};
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
        public static final String ERROR_FETCH_PRODUCTS = "Error al obtener información de productos: {}";
        public static final String ERROR_FETCH_TOURS = "Error al obtener información de tours: {}";
    }
}
