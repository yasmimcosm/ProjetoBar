package com.example.demo.yasmimJose.exception;

public class ContaFechada extends Exception {
    public ContaFechada() {
        super("A conta já está fechada.");
    }
}
