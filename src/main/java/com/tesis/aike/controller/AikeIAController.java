package com.tesis.aike.controller;

import com.tesis.aike.service.AikeIA;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> chatear(@RequestParam String pregunta) {
        if (pregunta == null || pregunta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La pregunta no puede estar vacía.");
        }
        String respuesta = chatGptService.obtenerRespuestaChat(pregunta);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/chat-guiado")
    public ResponseEntity<String> chatearGuiado(@RequestParam String contexto, @RequestParam String pregunta) {
        if (pregunta == null || pregunta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La pregunta no puede estar vacía.");
        }
        String respuesta = chatGptService.obtenerRespuestaConRolSistema(contexto, pregunta);
        return ResponseEntity.ok(respuesta);
    }
}
