package com.tesis.aike.controller;

import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.service.impl.AikeAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/aike/ia/text")
public class AikeIAController {

    private final AikeAIServiceImpl chatGptService;

    @Autowired
    public AikeIAController(AikeAIServiceImpl chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> sendTextPrompt(@RequestBody Map<String, String> payload) {
        String prompt = payload.get(ConstantValues.AikeAIConstant.PROMPT);
        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ConstantValues.AikeAIConstant.EMPTY_PROMPT);
        }
        return ResponseEntity.ok(chatGptService.promptResponse(prompt));

    }
}
