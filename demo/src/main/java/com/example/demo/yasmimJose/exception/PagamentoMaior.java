package com.example.demo.yasmimJose.exception;

public class PagamentoMaior extends Exception {
    public PagamentoMaior() {
        super("O valor do pagamento é maior que o total da conta.");
    }
}
