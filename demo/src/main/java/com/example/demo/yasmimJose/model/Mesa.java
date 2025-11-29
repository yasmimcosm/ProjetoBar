package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mesa")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer numero;

    private Integer capacidade;
    private String status; // LIVRE, OCUPADA

    public Mesa() {}
    public Mesa(Integer numero, Integer capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.status = "LIVRE";
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }
    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
