package com.tesis.aike.model.dto;

public class ChatApiResponse {
    private String respuesta;
    private String[] lines;

    public ChatApiResponse(String respuesta, String[] lines) {
        this.respuesta = respuesta;
        this.lines = lines;
    }

    public ChatApiResponse(String respuesta) {
        this(respuesta, respuesta == null ? new String[0] : respuesta.split("\n"));
    }

    public String getRespuesta() {
        return respuesta;
    }

    public String[] getLines() {
        return lines;
    }
}