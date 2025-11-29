package com.example.demo.yasmimJose;

import com.example.demo.yasmimJose.exception.*;

public class TestesBarCompleto {
    public static void main(String[] args) {
        Bar bar = new Bar();

        System.out.println("=== INÍCIO DOS TESTES COMPLETOS ===\n");

        // ================== CARDÁPIO ==================
        try {
            bar.addCardapio(1, "Ingresso Show", 50, 1);
            bar.addCardapio(2, "Cerveja", 10, 2);
            bar.addCardapio(3, "Hamburguer", 15, 3);
            bar.addCardapio(4, "Refrigerante", 8, 2);
            bar.addCardapio(5, "Pizza", 20, 3);
            System.out.println("✅ Cardápio completo criado.");
        } catch (Exception e) {
            System.out.println("❌ Erro no cardápio: " + e.getClass().getSimpleName());
        }

        // ================== ABRIR CONTAS ==================
        try {
            bar.abrirConta(101, 111222333, "Yasmim", true); // ingresso automático
            bar.abrirConta(102, 444555666, "Lucas", false);
            System.out.println("✅ Contas abertas corretamente.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao abrir contas: " + e.getClass().getSimpleName());
        }

        // ================== PEDIDOS ==================
        try {
            // Conta 101
            bar.addPedido(101, 2, 2); // 2 cervejas
            bar.addPedido(101, 3, 1); // 1 hamburguer
            bar.addPedido(101, 4, 3); // 3 refrigerantes
            bar.addPedido(101, 5, 2); // 2 pizzas

            // Conta 102
            bar.addPedido(102, 2, 1); // 1 cerveja
            bar.addPedido(102, 5, 1); // 1 pizza
            System.out.println("✅ Pedidos adicionados corretamente nas duas contas.");
        } catch (Exception e) {
            System.out.println("❌ Erro ao adicionar pedidos: " + e.getClass().getSimpleName());
        }

        // ================== TESTAR PEDIDO INVÁLIDO ==================
        try { bar.addPedido(999, 2, 1); } catch (
                ContaInexistente e) { System.out.println("✅ Conta inexistente detectada."); } catch (Exception e) { System.out.println("❌ Erro inesperado"); }
        try { bar.addPedido(101, 99, 1); } catch (
                ItemInexistente e) { System.out.println("✅ Item inexistente detectado."); } catch (Exception e) { System.out.println("❌ Erro inesperado"); }
        try { bar.addPedido(101, 2, 0); } catch (
                DadosInvalidos e) { System.out.println("✅ Quantidade inválida detectada."); } catch (Exception e) { System.out.println("❌ Erro inesperado"); }

        // ================== EXTRATO ANTES DO FECHAMENTO ==================
        try {
            System.out.println("\n--- Extrato da conta 101 antes do fechamento ---");
            bar.extratoDeConta(101);
        } catch (Exception e) { System.out.println("❌ Erro no extrato: " + e.getClass().getSimpleName()); }

        // ================== FECHAR CONTA 101 ==================
        try {
            double total101 = bar.fecharConta(101);
            System.out.println("\n✅ Conta 101 fechada. Total: R$" + total101);
        } catch (Exception e) { System.out.println("❌ Erro ao fechar conta: " + e.getClass().getSimpleName()); }

        // ================== TENTAR PEDIDO APÓS FECHAMENTO ==================
        try { bar.addPedido(101, 2, 1); System.out.println("❌ Pedido aceito após fechamento!"); }
        catch (ContaFechada e) { System.out.println("✅ Pedido bloqueado após fechamento."); }
        catch (Exception e) { System.out.println("❌ Erro inesperado"); }

        // ================== PAGAMENTOS PARCIAIS ==================
        try {
            bar.registrarPagamento(101, 50); // pagamento parcial
            bar.registrarPagamento(101, 40); // pagamento parcial
            System.out.println("✅ Pagamentos parciais registrados.");
        } catch (Exception e) { System.out.println("❌ Erro no pagamento: " + e.getClass().getSimpleName()); }

        // ================== PAGAMENTO MAIOR ==================
        try { bar.registrarPagamento(101, 1000); System.out.println("❌ Pagamento maior aceito!"); }
        catch (PagamentoMaior e) { System.out.println("✅ Pagamento maior bloqueado corretamente."); }
        catch (Exception e) { System.out.println("❌ Outro erro inesperado"); }

        // ================== EXTRATO FINAL ==================
        try {
            System.out.println("\n--- Extrato final da conta 101 ---");
            bar.extratoDeConta(101);
        } catch (Exception e) { System.out.println("❌ Erro no extrato final: " + e.getClass().getSimpleName()); }

        // ================== TESTES DE ABERTURA INVÁLIDA ==================
        try { bar.abrirConta(-1, 0, "", false); System.out.println("❌ Aceitou dados inválidos!"); }
        catch (DadosInvalidos e) { System.out.println("✅ Dados inválidos detectados corretamente."); }
        catch (Exception e) { System.out.println("❌ Outro erro inesperado"); }

        // ================== ABRIR CONTA 103 COM INGRESSOS MÚLTIPLOS ==================
        try {
            bar.abrirConta(103, 777888999, "Ana", true);
            bar.addPedido(103, 1, 3); // adiciona mais ingressos manualmente
            System.out.println("✅ Conta 103 com ingressos múltiplos criada corretamente.");
        } catch (Exception e) { System.out.println("❌ Erro na conta 103: " + e.getClass().getSimpleName()); }

        // ================== EXTRATO DA CONTA 103 ==================
        try {
            System.out.println("\n--- Extrato da conta 103 ---");
            bar.extratoDeConta(103);
        } catch (Exception e) { System.out.println("❌ Erro no extrato da conta 103: " + e.getClass().getSimpleName()); }

        System.out.println("\n=== FIM DOS TESTES COMPLETOS ===");
    }
}
