package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // coluna "senha" do banco
    @Column(nullable = false)
    private String senha;

    // coluna "papel" (ADMIN ou GARCOM)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Papel papel;

    public Usuario() {}

    public Usuario(String senha, Papel papel) {
        this.senha = senha;
        this.papel = papel;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Papel getPapel() { return papel; }
    public void setPapel(Papel papel) { this.papel = papel; }
}
