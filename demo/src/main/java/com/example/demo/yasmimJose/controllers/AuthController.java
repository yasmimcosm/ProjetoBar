package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.LoginDTO;
import com.example.demo.yasmimJose.model.Usuario;
import com.example.demo.yasmimJose.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // LOGIN — retorna somente String
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto) {
        return usuarioService.autenticar(dto.email, dto.senha)
                .map(u -> ResponseEntity.ok("Login realizado com sucesso!"))
                .orElseGet(() -> ResponseEntity.status(401).body("Credenciais inválidas"));
    }

    // criar usuário — também retorna String
    @PostMapping("/criar")
    public ResponseEntity<String> criar(
            @RequestBody LoginDTO dto,
            @RequestParam String nome,
            @RequestParam String papel) {

        Usuario u = usuarioService.criarUsuario(nome, dto.email, dto.senha, papel);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
