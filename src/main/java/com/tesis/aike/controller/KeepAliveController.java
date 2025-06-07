package com.tesis.aike.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KeepAliveController {

    @GetMapping("/")
    public String ok() {
        return "Aike API viva";
    }
}

