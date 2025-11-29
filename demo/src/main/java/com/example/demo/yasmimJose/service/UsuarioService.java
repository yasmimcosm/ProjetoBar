package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Usuario;
import com.example.demo.yasmimJose.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder;

    public UsuarioService(UsuarioRepository repo, BCryptPasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return repo.findByEmail(email)
                .filter(u -> encoder.matches(senha, u.getSenhaHash()));
    }

    public Usuario criarUsuario(String nome, String email, String senha, String papel) {
        String hash = encoder.encode(senha);
        Usuario u = new Usuario(nome, email, hash, papel);
        return repo.save(u);
    }
}
