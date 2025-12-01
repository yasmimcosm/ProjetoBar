package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // do ItemCardapio

    private String nome;       // nome do item
    private String categoria;  // categoria: BEBIDA / COMIDA
    private Double preco;      // valor do item

    private int tipo;          // 1=Ingresso, 2=Bebida, 3=Comida

    public Item() {} // construtor vazio necessário para JPA

    // construtor completo
    public Item(String nome, String categoria, Double preco, int tipo) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.tipo = tipo;
    }

    // Getters e Setters
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }
}
