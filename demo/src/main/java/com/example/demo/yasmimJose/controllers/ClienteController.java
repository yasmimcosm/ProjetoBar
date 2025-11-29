package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Conta;
import com.example.demo.yasmimJose.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    private final ContaService contaService;

    public ClienteController(ContaService contaService) { this.contaService = contaService; }

    @GetMapping("/conta/{id}")
    public ResponseEntity<?> verConta(@PathVariable Long id) {
        Conta c = contaService.getConta(id);
        return ResponseEntity.ok(c);
    }
}
