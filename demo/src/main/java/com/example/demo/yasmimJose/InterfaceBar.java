package com.example.demo.yasmimJose;

import com.example.demo.yasmimJose.exception.*;

import java.util.ArrayList;

public interface InterfaceBar {

    // Abre conta, agora com parâmetro de ingresso
    public void abrirConta(int numConta, int cpf, String nomeCliente, boolean ingresso)
            throws ContaAberta, DadosInvalidos;

    // Adiciona pedido
    public void addPedido(int numConta, int numItem, int quant)
            throws ContaFechada, ContaInexistente, ItemInexistente, DadosInvalidos;

    // Calcula valor da conta (com gorjeta)
    public double valorDaConta(int numConta) throws ContaInexistente;

    // Fecha a conta
    public double fecharConta(int numConta) throws ContaInexistente;

    // Adiciona item no cardápio
    public void addCardapio(int num, String nome, double valItem, int tipo)
            throws ItemJaCadastrado, DadosInvalidos;

    // Registra pagamento da conta
    public void registrarPagamento(int numConta, double val)
            throws PagamentoMaior, ContaInexistente, DadosInvalidos, ContaNaoFechada;

    // Extrato completo da conta
    public ArrayList<Consumo> extratoDeConta(int numConta) throws ContaInexistente;

    // Lista todo o cardápio
    public void listarCardapio();
}
