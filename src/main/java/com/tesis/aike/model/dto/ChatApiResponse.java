package com.tesis.aike.model.dto;

public class ChatApiResponse {

    private String lines;

    public ChatApiResponse(String lines) {
        this.lines = lines;
    }

    public String getLines() {
        return lines;
    }
}