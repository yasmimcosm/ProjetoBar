package com.example.demo.yasmimJose.exception;

public class ItemInexistente extends Exception {
    public ItemInexistente() {
        super("O item não existe no cardápio.");
    }
}
