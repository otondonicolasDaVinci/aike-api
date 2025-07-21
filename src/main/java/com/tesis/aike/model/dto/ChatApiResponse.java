package com.tesis.aike.model.dto;

public class ChatApiResponse {
    /**
     * Texto devuelto por la IA ya formateado para el cliente.
     */
    private String lines;

    public ChatApiResponse(String lines) {
        this.lines = lines;
    }

    public String getLines() {
        return lines;
    }
}