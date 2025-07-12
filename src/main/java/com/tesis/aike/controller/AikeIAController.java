package com.tesis.aike.controller;

import com.tesis.aike.model.dto.ChatApiResponse;
import com.tesis.aike.helper.ConstantValues;
import com.tesis.aike.service.impl.AikeAIServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/aike/ia/text")
public class    AikeIAController {

    private final AikeAIServiceImpl chatGptService;

    @Autowired
    public AikeIAController(AikeAIServiceImpl chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<?> sendTextPrompt(@RequestBody Map<String, String> payload) {
        String prompt = payload.get(ConstantValues.AikeAIConstant.PROMPT);

        if (prompt == null || prompt.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(ConstantValues.AikeAIConstant.EMPTY_PROMPT);
        }

        String iaResponseText = chatGptService.promptResponse(prompt);

        String[] lines = iaResponseText == null ? new String[0] : iaResponseText.split("\n");
        ChatApiResponse apiResponse = new ChatApiResponse(iaResponseText, lines);

        return ResponseEntity.ok(apiResponse);
    }
}