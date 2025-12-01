package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Papel;
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

    // autenticação apenas pela senha (pois não existe email / username)
    public Optional<Usuario> autenticar(String senha) {

        // autentica o administrador (exemplo simples)
        Optional<Usuario> admin = repo.findByPapel(Papel.ADMIN);

        // verifica senha usando BCrypt
        return admin.filter(u -> encoder.matches(senha, u.getSenha()));
    }

    public Usuario criarUsuario(String senha, Papel papel) {
        String senhaCriptografada = encoder.encode(senha);
        Usuario usuario = new Usuario(senhaCriptografada, papel);
        return repo.save(usuario);
    }

    // VERIFICAR SENHA DIRETO NO BANCO (SEM BCRYPT)
    public boolean verificarSenha(String senha) {
        Usuario user = repo.findBySenha(senha); // <--- AQUI ESTAVA O ERRO
        return user != null;
    }
}

