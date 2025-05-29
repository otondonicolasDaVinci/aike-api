package com.tesis.aike.service.impl;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.model.dto.ReservationDTO;
import com.tesis.aike.service.AikeAIService;
import com.tesis.aike.service.CabinService;
import com.tesis.aike.service.ReservationService;
import com.tesis.aike.utils.QueryDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AikeAIServiceImpl implements AikeAIService {

    private static final Logger logger = LoggerFactory.getLogger(AikeAIServiceImpl.class);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(ConstantValues.AikeAIService.DATE_PATTERN);

    private final ChatClient chat;
    private final CabinService cabinSvc;
    private final ReservationService resSvc;

    // <-- MODIFICACIÓN 1: Se añade la instrucción de sistema como una constante.
    private static final String SYSTEM_INSTRUCTION = """
            Eres un asistente virtual amigable y servicial para "Cabañas Aike".
            Tu propósito principal es responder preguntas sobre la disponibilidad de cabañas y las reservas de los usuarios.
            Basa tus respuestas estrictamente en la información de contexto que se te proporciona en cada prompt.
            No ofrezcas información que no esté en el contexto. Si la pregunta del usuario no se puede responder con el contexto dado,
            indica amablemente que no tienes acceso a esa información. Sé siempre cortés y profesional.
            """;

    @Autowired
    public AikeAIServiceImpl(ChatClient.Builder builder,
                             CabinService cabinSvc,
                             ReservationService resSvc) {
        // <-- MODIFICACIÓN 2: Se configura el cliente de chat con el prompt de sistema por defecto.
        this.chat = builder
                .defaultSystem(SYSTEM_INSTRUCTION)
                .build();
        this.cabinSvc = cabinSvc;
        this.resSvc = resSvc;
    }

    public String promptResponse(String msg) {
        if (msg == null || msg.trim().isEmpty())
            return ConstantValues.AikeAIConstant.EMPTY_PROMPT;

        Long uid = getUserId();
        boolean isAdmin = currentUserIsAdmin();

        if (QueryDetector.isSensitiveDataQuery(msg) && !isAdmin)
            return "Necesitas permisos de administrador para ver esa información.";

        boolean askCabins = QueryDetector.isAvailableCabinsQuery(msg);
        boolean askOwnRes = QueryDetector.isReservationQuery(msg);
        boolean askAllRes = QueryDetector.isAllReservationsQuery(msg);

        String prompt;

        if (askCabins) {
            try {
                List<CabinDTO> cabs = cabinSvc.findByAvailableTrue();
                String lines = cabs.stream()
                        .map(c -> String.format("- Cabaña '%s' (Capacidad: %d)%s",
                                c.getName(), c.getCapacity(),
                                c.getDescription() == null || c.getDescription().isBlank()
                                        ? "" : ": " + c.getDescription()))
                        .collect(Collectors.joining("\n"));

                String info = cabs.isEmpty()
                        ? ConstantValues.AikeAIService.NO_AVAILABLE_CABINS
                        : String.format(ConstantValues.AikeAIService.AVAILABLE_CABINS_TEMPLATE, lines);

                prompt = String.format(ConstantValues.AikeAIService.CONTEXT_CABINS_PROMPT, info, msg);
            } catch (Exception e) {
                logger.error(ConstantValues.LoggerMessages.ERROR_FETCH_CABINS, e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_CABINS;
            }
        } else if (askOwnRes) {
            try {
                if (uid == null) {
                    prompt = String.format(ConstantValues.AikeAIService.USER_NOT_IDENTIFIED_TEMPLATE, msg);
                } else {
                    List<ReservationDTO> list = resSvc.findByUserId(uid);
                    prompt = buildReservationContext(uid, list, msg);
                }
            } catch (Exception e) {
                logger.error(ConstantValues.LoggerMessages.ERROR_FETCH_RESERVATIONS, e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_RESERVATIONS;
            }
        } else if (askAllRes) {
            if (!isAdmin) return "Necesitas permisos de administrador para ver todas las reservas.";
            try {
                List<ReservationDTO> list = resSvc.findAll();
                prompt = buildReservationContext(-1L, list, msg); // -1 indica contexto global
            } catch (Exception e) {
                logger.error(ConstantValues.LoggerMessages.ERROR_FETCH_RESERVATIONS, e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_RESERVATIONS;
            }
        } else {
            return ConstantValues.AikeAIService.NOT_CONTEXT;
        }


        try {
            // Ya no es necesario pasar el rol "system" aquí, porque fue configurado por defecto.
            return chat.prompt()
                    .user(prompt)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error(ConstantValues.LoggerMessages.ERROR_CALL_OPENAI, e.getMessage(), e);
            return ConstantValues.AikeAIService.ERROR_OPENAI;
        }
    }

    private String buildReservationContext(Long uid, List<ReservationDTO> list, String originalMsg) {
        String lines = list.stream()
                .map(r -> String.format(ConstantValues.AikeAIService.RESERVATION_LINE_TEMPLATE,
                        r.getId(), r.getCabin().getName(),
                        r.getStartDate().format(DATE_TIME_FORMATTER), r.getEndDate().format(DATE_TIME_FORMATTER), r.getStatus()))
                .collect(Collectors.joining("\n"));

        String info = list.isEmpty()
                ? ConstantValues.AikeAIService.NO_RESERVATIONS
                : String.format(ConstantValues.AikeAIService.USER_RESERVATIONS_TEMPLATE, lines);

        return String.format(ConstantValues.AikeAIService.CONTEXT_RESERVATIONS_PROMPT, uid, info, originalMsg);
    }

    private Long getUserId() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null || !a.isAuthenticated()) return null;
        Object p = a.getPrincipal();
        try {
            if (p instanceof User u) return Long.parseLong(u.getUsername());
            if (p instanceof org.springframework.security.oauth2.core.user.OAuth2User o) {
                String id = o.getAttribute("sub");
                return id == null ? null : Long.parseLong(id);
            }
            if (p instanceof String s) return Long.parseLong(s);
        } catch (Exception e) {
            logger.error(ConstantValues.LoggerMessages.ERROR_AUTH_ID, e.getMessage(), e);
        }
        return null;
    }

    private boolean currentUserIsAdmin() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        if (a == null || !a.isAuthenticated()) return false;
        return a.getAuthorities().stream()
                .anyMatch(granted -> granted.getAuthority().equals("ROLE_ADMIN"));
    }
}