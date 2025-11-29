package com.example.demo.yasmimJose;

public class TestBarWmySql {
    public static void main(String[] args) {
        Bar bar = new Bar();

        try {
            System.out.println("=== ADICIONANDO ITENS AO CARDÁPIO ===");
            bar.addCardapio(1, "Ingresso", 10.0, 1);  // ingresso
            bar.addCardapio(2, "Cerveja", 8.0, 2);    // bebida
            bar.addCardapio(3, "Pizza", 20.0, 3);     // comida
            bar.listarCardapio();

            System.out.println("\n=== ABRINDO CONTA COM INGRESSO ===");
            bar.abrirConta(101, 123456789, "Alice", true);

            System.out.println("\n=== ADICIONANDO PEDIDOS ===");
            bar.addPedido(101, 2, 2); // 2 cervejas
            bar.addPedido(101, 3, 1); // 1 pizza

            System.out.println("\n=== VALOR DA CONTA ===");
            double valor = bar.valorDaConta(101);
            System.out.println("Valor total (com gorjetas): R$" + valor);

            System.out.println("\n=== FECHANDO CONTA ===");
            double totalFinal = bar.fecharConta(101);
            System.out.println("Conta fechada. Total final: R$" + totalFinal);

            System.out.println("\n=== REGISTRANDO PAGAMENTOS ===");
            bar.registrarPagamento(101, 20); // pagamento parcial
            bar.registrarPagamento(101, 30); // pagamento final

            System.out.println("\n=== EXTRATO DA CONTA ===");
            bar.extratoDeConta(101);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
