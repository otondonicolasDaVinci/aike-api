package com.tesis.aike.controller;

import com.tesis.aike.helper.ConstantsValues;
import com.tesis.aike.service.impl.AikeAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ia")
public class AikeIAController {

    private final AikeAIServiceImpl chatGptService;

    @Autowired
    public AikeIAController(AikeAIServiceImpl chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> enviarPregunta(@RequestBody Map<String, String> payload) {
        String pregunta = payload.get(ConstantsValues.AikeAIConstants.PREGUNTA);
        if (pregunta == null || pregunta.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ConstantsValues.AikeAIConstants.PREGUNTA_VACIA);
        }
        String respuesta = chatGptService.obtenerRespuestaChat(pregunta);
        return ResponseEntity.ok(respuesta);
    }
}
