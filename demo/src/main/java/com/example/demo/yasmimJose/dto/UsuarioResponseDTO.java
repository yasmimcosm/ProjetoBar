package com.example.demo.yasmimJose.dto;

public class UsuarioResponseDTO {
    public Long id;
    public String nome;
    public String email;
    public String papel;

    public UsuarioResponseDTO(Long id, String nome, String email, String papel) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.papel = papel;
    }
}