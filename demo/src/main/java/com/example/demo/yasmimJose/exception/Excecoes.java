package com.example.demo.yasmimJose.exception;

public class Excecoes extends RuntimeException {
    public class ContaAberta extends Exception {}
    public class DadosInvalidos extends Exception {}
    public class ContaInexistente extends Exception {}
    public class ContaFechada extends Exception {}
    public class ItemInexistente extends Exception {}
    public class ItemJaCadastrado extends Exception {}
    public class PagamentoMaior extends Exception {}
    public class ContaNaoFechada extends Exception {}

    public Excecoes(String message) {
        super(message);
    }
}
