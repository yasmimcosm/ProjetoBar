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
    private ItemCardapio item;

    private Integer quantidade;
    private Double precoUnit;
    private Double subtotal;
    private String status; // ATIVO, CANCELADO
    private String justificativaCancelamento;

    public Pedido() {}
    public Pedido(Conta conta, ItemCardapio item, Integer quantidade) {
        this.conta = conta;
        this.item = item;
        this.quantidade = quantidade;
        this.precoUnit = item.getPreco();
        this.subtotal = this.precoUnit * this.quantidade;
        this.status = "ATIVO";
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
    public ItemCardapio getItem() { return item; }
    public void setItem(ItemCardapio item) { this.item = item; }
    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }
    public Double getPrecoUnit() { return precoUnit; }
    public void setPrecoUnit(Double precoUnit) { this.precoUnit = precoUnit; }
    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getJustificativaCancelamento() { return justificativaCancelamento; }
    public void setJustificativaCancelamento(String justificativaCancelamento) { this.justificativaCancelamento = justificativaCancelamento; }
}
