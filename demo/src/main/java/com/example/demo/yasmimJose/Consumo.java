package com.example.demo.yasmimJose;

public class Consumo {
    private Item item;
    private int quantidade;

    public Consumo(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public Item getItem() { return item; }
    public int getQuantidade() { return quantidade; }
    public double getTotal() { return item.getValor() * quantidade; }
}
