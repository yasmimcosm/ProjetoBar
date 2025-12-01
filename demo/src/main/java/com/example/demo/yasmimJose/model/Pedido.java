package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome; // nome do item
    private Double preco;
    private Integer quantidade;

    @ManyToOne
    private Conta conta;

    private Long itemId; // referência ao item do cardápio

    private String motivoCancelamento; // novo campo

    public Pedido() {}

    public Pedido(String nome, Double preco, Integer quantidade, Conta conta, Long itemId) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
        this.conta = conta;
        this.itemId = itemId;


    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
}
