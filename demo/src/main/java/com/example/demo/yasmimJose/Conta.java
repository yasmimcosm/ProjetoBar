package com.example.demo.yasmimJose;

import java.util.ArrayList;

public class Conta {
    private int numConta, cpf;
    private String nomeCliente;
    private boolean fechada;
    private double pagamentos;
    private ArrayList<Consumo> consumos;

    public Conta(int numConta, int cpf, String nomeCliente) {
        this.numConta = numConta;
        this.cpf = cpf;
        this.nomeCliente = nomeCliente;
        this.fechada = false;
        this.pagamentos = 0;
        this.consumos = new ArrayList<>();
    }

    public int getNumConta() { return numConta; }
    public boolean isFechada() { return fechada; }
    public void fechar() { this.fechada = true; }
    public ArrayList<Consumo> getConsumos() { return consumos; }
    public double getPagamentos() { return pagamentos; }
    public void addConsumo(Consumo c) { consumos.add(c); }
    public void addPagamento(double val) { pagamentos += val; }
}

