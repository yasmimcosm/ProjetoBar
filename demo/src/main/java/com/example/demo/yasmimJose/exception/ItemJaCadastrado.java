package com.example.demo.yasmimJose.exception;

public class ItemJaCadastrado extends Exception {
    public ItemJaCadastrado() {
        super("O item já está cadastrado no cardápio.");
    }
}
