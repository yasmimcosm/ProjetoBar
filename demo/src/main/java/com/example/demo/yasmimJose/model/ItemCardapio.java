package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_cardapio")
public class ItemCardapio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String categoria; // bebida, comida, etc.
    private Double preco;

    public ItemCardapio() {}
    public ItemCardapio(String nome, String categoria, Double preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
}
