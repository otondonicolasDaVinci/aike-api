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
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(ConstantValues.AikeAIService.DATE_PATTERN);

    private final ChatClient chatClient;
    private final CabinService cabinService;
    private final ReservationService reservationService;

    @Autowired
    public AikeAIServiceImpl(ChatClient.Builder chatClientBuilder,
                             CabinService cabinService,
                             ReservationService reservationService) {
        this.chatClient = chatClientBuilder.build();
        this.cabinService = cabinService;
        this.reservationService = reservationService;
    }

    public String promptResponse(String message) {
        if (message == null || message.trim().isEmpty()) {
            return ConstantValues.AikeAIConstant.EMPTY_PROMPT;
        }

        String promptToSend = message;
        boolean askCabins = QueryDetector.isAvailableCabinsQuery(message);
        boolean askMyReservations = QueryDetector.isReservationQuery(message);

        if (askCabins) {
            try {
                List<CabinDTO> cabins = cabinService.findByAvailableTrue();
                String cabinLines = cabins.stream()
                        .map(c -> String.format("- Cabaña '%s' (Capacidad: %d)%s",
                                c.getName(), c.getCapacity(),
                                c.getDescription() != null && !c.getDescription().isBlank()
                                        ? ": " + c.getDescription()
                                        : ""))
                        .collect(Collectors.joining("\n"));

                String cabinsInfo = cabins.isEmpty()
                        ? ConstantValues.AikeAIService.NO_AVAILABLE_CABINS
                        : String.format(ConstantValues.AikeAIService.AVAILABLE_CABINS_TEMPLATE, cabinLines);

                promptToSend = String.format(
                        ConstantValues.AikeAIService.CONTEXT_CABINS_PROMPT,
                        cabinsInfo, message);
            } catch (Exception e) {
                logger.error(ConstantValues.LoggerMessages.ERROR_FETCH_CABINS, e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_CABINS;
            }
        } else if (askMyReservations) {
            try {
                Integer userId = getAuthenticatedUserId();
                if (userId == null) {
                    promptToSend = String.format(
                            ConstantValues.AikeAIService.USER_NOT_IDENTIFIED_TEMPLATE, message);
                } else {
                    List<ReservationDTO> reservations = reservationService.findByUserId(userId);

                    String reservationLines = reservations.stream()
                            .map(r -> String.format(
                                    ConstantValues.AikeAIService.RESERVATION_LINE_TEMPLATE,
                                    r.getId(), r.getCabin().getName(),
                                    r.getStartDate().format(DATE_FORMATTER),
                                    r.getEndDate().format(DATE_FORMATTER),
                                    r.getStatus()))
                            .collect(Collectors.joining("\n"));

                    String info = reservations.isEmpty()
                            ? ConstantValues.AikeAIService.NO_RESERVATIONS
                            : String.format(ConstantValues.AikeAIService.USER_RESERVATIONS_TEMPLATE, reservationLines);

                    promptToSend = String.format(
                            ConstantValues.AikeAIService.CONTEXT_RESERVATIONS_PROMPT,
                            userId, info, message);
                }
            } catch (Exception e) {
                logger.error(ConstantValues.LoggerMessages.ERROR_FETCH_RESERVATIONS, e.getMessage(), e);
                return ConstantValues.AikeAIService.ERROR_RESERVATIONS;
            }
        }

        try {
            return chatClient.prompt().user(promptToSend).call().content();
        } catch (Exception e) {
            logger.error(ConstantValues.LoggerMessages.ERROR_CALL_OPENAI, e.getMessage(), e);
            return ConstantValues.AikeAIService.ERROR_OPENAI;
        }
    }

    private Integer getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return null;

        Object principal = auth.getPrincipal();
        try {
            if (principal instanceof User u) return Integer.parseInt(u.getUsername());
            if (principal instanceof org.springframework.security.oauth2.core.user.OAuth2User o) {
                String id = o.getAttribute("sub");
                return id != null ? Integer.parseInt(id) : null;
            }
            if (principal instanceof String s) return Integer.parseInt(s);
        } catch (Exception e) {
            logger.error(ConstantValues.LoggerMessages.ERROR_AUTH_ID, e.getMessage(), e);
        }
        return null;
    }

    public String systemRoleResponse(String systemMessage, String userMessage) {
        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ConstantValues.AikeAIConstant.EMPTY_PROMPT;
        }
        try {
            return chatClient.prompt().system(systemMessage).user(userMessage).call().content();
        } catch (Exception e) {
            logger.error(ConstantValues.LoggerMessages.ERROR_SYSTEM_OPENAI, e.getMessage());
            return ConstantValues.AikeAIService.ERROR_OPENAI;
        }
    }
}
