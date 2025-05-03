package com.tesis.aike.service.impl;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.model.dto.CabinDTO;
import com.tesis.aike.model.entity.ReservationsEntity;
import com.tesis.aike.service.AikeAIService;
import com.tesis.aike.service.CabinService;
import com.tesis.aike.service.ReservationsService;
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
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(ConstantValues.AikeAIService.DATE_PATTERN);

    private final ChatClient chatClient;
    private final CabinService cabinService;
    private final ReservationsService reservationsService;

    @Autowired
    public AikeAIServiceImpl(ChatClient.Builder chatClientBuilder,
                             CabinService cabinService,
                             ReservationsService reservationsService) {
        this.chatClient = chatClientBuilder.build();
        this.cabinService = cabinService;
        this.reservationsService = reservationsService;
    }

    @Override
    public String promptResponse(String message) {
        if (message == null || message.trim().isEmpty()) {
            return ConstantValues.AikeAIConstant.EMPTY_PROMPT;
        }

        String promptToSend = message;
        boolean askCabins = QueryDetector.isAvailableCabinsQuery(message);
        boolean askMyReservations = QueryDetector.isReservationQuery(message);

        if (askCabins) {
            try {
                List<CabinDTO> availableCabins = cabinService.findByAvailableTrue();
                String cabinLines = availableCabins.stream()
                        .map(cabana -> String.format("- Cabaña '%s' (Capacidad: %d)%s",
                                cabana.getName(),
                                cabana.getCapacity(),
                                cabana.getDescription() != null && !cabana.getDescription().trim().isEmpty()
                                        ? ": " + cabana.getDescription()
                                        : ""))
                        .collect(Collectors.joining("\n"));

                String cabinsInfo = availableCabins.isEmpty()
                        ? ConstantValues.AikeAIService.NO_AVAILABLE_CABINS
                        : String.format(ConstantValues.AikeAIService.AVAILABLE_CABINS_TEMPLATE, cabinLines);

                promptToSend = String.format(
                        ConstantValues.AikeAIService.CONTEXT_CABINS_PROMPT,
                        cabinsInfo,
                        message
                );
            } catch (Exception e) {
                logger.error("Error al obtener información de cabañas: {}", e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_CABINS;
            }
        } else if (askMyReservations) {
            try {
                Integer userId = getAuthenticatedUserId();

                if (userId == null) {
                    promptToSend = String.format(
                            ConstantValues.AikeAIService.USER_NOT_IDENTIFIED_TEMPLATE,
                            message);
                } else {
                    List<ReservationsEntity> myReservations = reservationsService.obtenerReservasPorUsuario(userId);

                    String reservationLines = myReservations.stream()
                            .map(reserva -> String.format(
                                    "- Reserva ID: %d | Cabaña ID: %d | Desde: %s | Hasta: %s | Estado: %s",
                                    reserva.getId(),
                                    reserva.getCabinId(),
                                    reserva.getStartDate().format(DATE_FORMATTER),
                                    reserva.getEndDate().format(DATE_FORMATTER),
                                    reserva.getStatus()))
                            .collect(Collectors.joining("\n"));

                    String reservationsInfo = myReservations.isEmpty()
                            ? ConstantValues.AikeAIService.NO_RESERVATIONS
                            : String.format(ConstantValues.AikeAIService.USER_RESERVATIONS_TEMPLATE, reservationLines);

                    promptToSend = String.format(
                            ConstantValues.AikeAIService.CONTEXT_RESERVATIONS_PROMPT,
                            userId,
                            reservationsInfo,
                            message
                    );
                }
            } catch (Exception e) {
                logger.error("Error al obtener información de reservas: {}", e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_RESERVATIONS;
            }
        }

        try {
            return chatClient.prompt()
                    .user(promptToSend)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("Error al llamar a la API OpenAI: {}", e.getMessage(), e);
            return ConstantValues.AikeAIService.ERROR_OPENAI;
        }
    }

    private Integer getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        try {
            if (principal instanceof User user) {
                return Integer.parseInt(user.getUsername());
            }
            if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User oauth) {
                String id = oauth.getAttribute("sub");
                return id != null ? Integer.parseInt(id) : null;
            }
            if (principal instanceof String str) {
                return Integer.parseInt(str);
            }
        } catch (Exception e) {
            logger.error("No se pudo obtener ID de usuario autenticado: {}", e.getMessage(), e);
        }
        return null;
    }

    public String systemRoleResponse(String systemMessage, String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ConstantValues.AikeAIConstant.EMPTY_PROMPT;
        }

        try {
            return chatClient.prompt()
                    .system(systemMessage)
                    .user(userMessage)
                    .call()
                    .content();
        } catch (Exception e) {
            logger.error("Error al usar rol de sistema con OpenAI: {}", e.getMessage());
            return ConstantValues.AikeAIService.ERROR_OPENAI;
        }
    }
}
