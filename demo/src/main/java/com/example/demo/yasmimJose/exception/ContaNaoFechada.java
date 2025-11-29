package com.example.demo.yasmimJose.exception;

public class ContaNaoFechada extends Exception {
    public ContaNaoFechada() {
        super("A conta ainda não foi fechada.");
    }
}
