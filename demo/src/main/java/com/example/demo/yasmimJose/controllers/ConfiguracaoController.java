package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Configuracao;
import com.example.demo.yasmimJose.service.ConfiguracaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuracoes")
public class ConfiguracaoController {

    private final ConfiguracaoService service;

    public ConfiguracaoController(ConfiguracaoService service) {
        this.service = service;
    }

    // GET para carregar valores atuais
    @GetMapping
    public ResponseEntity<Configuracao> buscarAtual() {
        Configuracao config = service.buscarAtual();
        if (config != null) {
            return ResponseEntity.ok(config);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PUT para atualizar
    @PutMapping
    public ResponseEntity<Configuracao> atualizar(@RequestBody Configuracao config) {
        try {
            Configuracao atualizado = service.salvar(config);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
