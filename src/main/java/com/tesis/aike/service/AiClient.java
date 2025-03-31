package com.tesis.aike.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ui.Model;

public interface ChatModel extends Model<Prompt, ChatResponse> {
    default String call(String message) {

    }

    @Override
    ChatResponse call(Prompt prompt);
}
