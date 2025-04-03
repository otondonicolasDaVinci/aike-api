package com.tesis.aike.service;

    import org.springframework.ai.chat.model.ChatResponse;
    import org.springframework.ai.chat.prompt.Prompt;

    public interface ChatModel {
        default String call(String message) {
            // Implement your logic here
            return null;
        }

        ChatResponse call(Prompt prompt);
    }