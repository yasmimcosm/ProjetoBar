package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.LoginDTO;
import com.example.demo.yasmimJose.model.Papel;
import com.example.demo.yasmimJose.model.Usuario;
import com.example.demo.yasmimJose.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // LOGIN — agora só usa a senha
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        boolean autorizado = usuarioService.verificarSenha(loginDTO.getSenha());

        return ResponseEntity.ok().body(Map.of("autorizado", autorizado));
    }


    // Criar usuário — recebe apenas senha e papel
    @PostMapping("/criar")
    public ResponseEntity<String> criar(
            @RequestParam String senha,
            @RequestParam String papel) {

        Usuario u = usuarioService.criarUsuario(
                senha,
                Papel.valueOf(papel.toUpperCase())
        );

        return ResponseEntity.ok("Usuário criado com sucesso!");
    }
}
