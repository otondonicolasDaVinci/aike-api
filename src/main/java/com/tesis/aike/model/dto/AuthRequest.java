package com.tesis.aike.model.dto;

public class AuthRequest {
    private String user;
    private String password;

    public AuthRequest() {
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}