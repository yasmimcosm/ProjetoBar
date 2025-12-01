package com.example.demo.yasmimJose.dto;

public class MesaUpdateDTO {
    private Integer numero;
    private Integer capacidade;
    private Boolean disponivel;

    // Getters e Setters
    public Integer getNumero() { return numero; }
    public void setNumero(Integer numero) { this.numero = numero; }

    public Integer getCapacidade() { return capacidade; }
    public void setCapacidade(Integer capacidade) { this.capacidade = capacidade; }

    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
}
