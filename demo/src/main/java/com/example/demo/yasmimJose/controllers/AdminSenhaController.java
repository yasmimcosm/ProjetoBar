package com.example.demo.yasmimJose.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminSenhaController {

    @GetMapping("/adminSenha")
    public String adminSenha() {
        return "adminSenha"; // abre o adminSenha.html em templates
    }
}
