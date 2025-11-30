package com.example.demo.yasmimJose.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/teste")
    public String teste() {
        return "Back-end funcionando!";
    }
}