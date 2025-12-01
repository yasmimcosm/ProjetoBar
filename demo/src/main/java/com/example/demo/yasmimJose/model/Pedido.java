package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta conta;

    @ManyToOne
    private Item item; // substituído ItemCardapio por Item

    private Integer quantidade;
    private Double precoUnit;
    private Double subtotal;
    private String status; // ATIVO, CANCELADO
    private String justificativaCancelamento;

    public Pedido() {}

    public Pedido(Conta conta, Item item, Integer quantidade) {
        this.conta = conta;
        this.item = item;
        this.quantidade = quantidade;
        this.precoUnit = item.getPreco();
        this.subtotal = this.precoUnit * this.quantidade;
        this.status = "ATIVO";
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }

    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
        atualizarSubtotal(); // recalcula subtotal ao alterar quantidade
    }

    public Double getPrecoUnit() { return precoUnit; }
    public void setPrecoUnit(Double precoUnit) { this.precoUnit = precoUnit; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getJustificativaCancelamento() { return justificativaCancelamento; }
    public void setJustificativaCancelamento(String justificativaCancelamento) {
        this.justificativaCancelamento = justificativaCancelamento;
    }

    // Método auxiliar para recalcular subtotal
    public void atualizarSubtotal() {
        if (this.precoUnit != null && this.quantidade != null) {
            this.subtotal = this.precoUnit * this.quantidade;
        }
    }
}
