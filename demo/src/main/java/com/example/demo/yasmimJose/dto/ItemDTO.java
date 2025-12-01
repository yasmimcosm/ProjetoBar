package com.example.demo.yasmimJose.dto;

public class ItemDTO {
    private String nome;
    private String categoria; // BEBIDA / COMIDA
    private Double preco;
    private int tipo;         // 1=Ingresso, 2=Bebida, 3=Comida

    public ItemDTO() {}

    public ItemDTO(String nome, String categoria, Double preco, int tipo) {
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
        this.tipo = tipo;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public int getTipo() { return tipo; }
    public void setTipo(int tipo) { this.tipo = tipo; }
}
