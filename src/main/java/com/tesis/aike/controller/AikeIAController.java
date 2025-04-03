package com.tesis.aike.controller;

import com.tesis.aike.service.AikeIA;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AikeIAController {

    private final AikeIA chatGptService;

    public AikeIAController(AikeIA chatGptService) {
        this.chatGptService = chatGptService;
    }

    @GetMapping("/chat")
    public String chatear(@RequestParam String pregunta) {
        return chatGptService.obtenerRespuestaChat(pregunta);
    }

    @GetMapping("/chat-guiado")
    public String chatearGuiado(@RequestParam String contexto, @RequestParam String pregunta) {
        return chatGptService.obtenerRespuestaConRolSistema(contexto, pregunta);
    }
}