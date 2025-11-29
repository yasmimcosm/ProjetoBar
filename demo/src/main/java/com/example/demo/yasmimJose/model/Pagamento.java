package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagamento")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conta conta;

    private Double valor;
    private String tipo; // DINHEIRO, CARTAO, PIX...
    private boolean parcial;
    private LocalDateTime data;

    public Pagamento() {}
    public Pagamento(Conta conta, Double valor, String tipo, boolean parcial) {
        this.conta = conta;
        this.valor = valor;
        this.tipo = tipo;
        this.parcial = parcial;
        this.data = LocalDateTime.now();
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Conta getConta() { return conta; }
    public void setConta(Conta conta) { this.conta = conta; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public boolean isParcial() { return parcial; }
    public void setParcial(boolean parcial) { this.parcial = parcial; }
    public LocalDateTime getData() { return data; }
    public void setData(LocalDateTime data) { this.data = data; }
}
