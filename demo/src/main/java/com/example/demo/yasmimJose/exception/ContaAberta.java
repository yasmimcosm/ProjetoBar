package com.example.demo.yasmimJose.exception;

public class ContaAberta extends Exception {
    public ContaAberta() {
        super("A conta já está aberta.");
    }
}
