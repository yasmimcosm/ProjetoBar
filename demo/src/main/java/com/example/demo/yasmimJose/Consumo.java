package com.example.demo.yasmimJose;

import com.example.demo.yasmimJose.model.Item;

public class Consumo {
    private Item item;
    private int quantidade;

    public Consumo(Item item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public Item getItem() { return item; }
    public int getQuantidade() { return quantidade; }

    public double getTotal() {
        return item.getPreco() * quantidade; // usa 'preco' do Item
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
