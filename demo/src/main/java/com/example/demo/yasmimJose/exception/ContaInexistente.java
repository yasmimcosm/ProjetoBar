package com.example.demo.yasmimJose.exception;

public class ContaInexistente extends Exception {
    public ContaInexistente() {
        super("A conta não existe.");
    }
}
