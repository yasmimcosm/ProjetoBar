package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracoes")
public class Configuracao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precoCouvert;
    private Double gorjetaComida;
    private Double gorjetaBebida;

    public Configuracao() {}

    public Configuracao(Double precoCouvert, Double gorjetaComida, Double gorjetaBebida) {
        this.precoCouvert = precoCouvert;
        this.gorjetaComida = gorjetaComida;
        this.gorjetaBebida = gorjetaBebida;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getPrecoCouvert() { return precoCouvert; }
    public void setPrecoCouvert(Double precoCouvert) { this.precoCouvert = precoCouvert; }

    public Double getGorjetaComida() { return gorjetaComida; }
    public void setGorjetaComida(Double gorjetaComida) { this.gorjetaComida = gorjetaComida; }

    public Double getGorjetaBebida() { return gorjetaBebida; }
    public void setGorjetaBebida(Double gorjetaBebida) { this.gorjetaBebida = gorjetaBebida; }
}
