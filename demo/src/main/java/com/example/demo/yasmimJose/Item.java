package com.example.demo.yasmimJose;

public class Item {
    private int numero;
    private String nome;
    private double valor;
    private int tipo; // 1=Ingresso, 2=Bebida, 3=Comida

    public Item(int numero, String nome, double valor, int tipo) {
        this.numero = numero;
        this.nome = nome;
        this.valor = valor;
        this.tipo = tipo;
    }

    public int getNumero() { return numero; }
    public String getNome() { return nome; }
    public double getValor() { return valor; }
    public int getTipo() { return tipo; }
}
