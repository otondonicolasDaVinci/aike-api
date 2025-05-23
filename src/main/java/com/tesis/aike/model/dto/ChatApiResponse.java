package com.tesis.aike.model.dto;

public class ChatApiResponse {
    private String respuesta;

    public ChatApiResponse(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }
}