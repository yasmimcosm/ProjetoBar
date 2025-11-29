package com.example.demo.yasmimJose;
import com.example.demo.yasmimJose.exception.*;

import java.sql.*;
import java.util.ArrayList;

public class Bar implements InterfaceBar {

    public Bar() {
        // não precisamos de listas, tudo vai pro banco
    }

    // ===== MÉTODOS AUXILIARES =====
    private Connection getConexao() throws SQLException {
        return Conexao.conectar();
    }

    private void verificarContaExiste(int numConta) throws ContaInexistente, SQLException {
        String sql = "SELECT * FROM conta WHERE numConta = ?";
        try (Connection conn = getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numConta);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new ContaInexistente();
        }
    }

    private void verificarItemExiste(int numItem) throws ItemInexistente, SQLException {
        String sql = "SELECT * FROM item WHERE numero = ?";
        try (Connection conn = getConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numItem);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new ItemInexistente();
        }
    }

    // ===== IMPLEMENTAÇÃO =====
    @Override
    public void abrirConta(int numConta, int cpf, String nomeCliente, boolean ingresso) throws ContaAberta, DadosInvalidos {
        if (numConta <= 0 || cpf <= 0 || nomeCliente == null || nomeCliente.trim().isEmpty())
            throw new DadosInvalidos();
        try (Connection conn = getConexao()) {
            // verifica se já existe
            String sqlCheck = "SELECT * FROM conta WHERE numConta = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, numConta);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) throw new ContaAberta();

            // insere nova conta
            String sqlInsert = "INSERT INTO conta (numConta, cpf, nomeCliente, fechada, pagamentos) VALUES (?, ?, ?, false, 0)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, numConta);
            psInsert.setInt(2, cpf);
            psInsert.setString(3, nomeCliente);
            psInsert.executeUpdate();

            // insere ingresso se solicitado
            if (ingresso) {
                try {
                    String sqlItem = "SELECT * FROM item WHERE numero = 1"; // item 1 = ingresso
                    PreparedStatement psItem = conn.prepareStatement(sqlItem);
                    ResultSet rsItem = psItem.executeQuery();
                    if (rsItem.next()) {
                        String sqlConsumo = "INSERT INTO consumo (numConta, numItem, quantidade) VALUES (?, ?, ?)";
                        PreparedStatement psConsumo = conn.prepareStatement(sqlConsumo);
                        psConsumo.setInt(1, numConta);
                        psConsumo.setInt(2, 1);
                        psConsumo.setInt(3, 1);
                        psConsumo.executeUpdate();
                    } else {
                        System.out.println("Item ingresso não cadastrado no cardápio.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPedido(int numConta, int numItem, int quant) throws ContaFechada, ContaInexistente, ItemInexistente, DadosInvalidos {
        if (numConta <= 0 || numItem <= 0 || quant <= 0) throw new DadosInvalidos();
        try (Connection conn = getConexao()) {
            // verifica conta
            verificarContaExiste(numConta);
            String sqlFechada = "SELECT fechada FROM conta WHERE numConta = ?";
            PreparedStatement psFechada = conn.prepareStatement(sqlFechada);
            psFechada.setInt(1, numConta);
            ResultSet rs = psFechada.executeQuery();
            if (rs.next() && rs.getBoolean("fechada")) throw new ContaFechada();

            // verifica item
            verificarItemExiste(numItem);

            // insere consumo
            String sqlInsert = "INSERT INTO consumo (numConta, numItem, quantidade) VALUES (?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, numConta);
            psInsert.setInt(2, numItem);
            psInsert.setInt(3, quant);
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double valorDaConta(int numConta) throws ContaInexistente {
        double total = 0;
        try (Connection conn = getConexao()) {
            verificarContaExiste(numConta);

            String sql = "SELECT c.quantidade, i.valor, i.tipo FROM consumo c JOIN item i ON c.numItem = i.numero WHERE c.numConta = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, numConta);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double valor = rs.getDouble("valor") * rs.getInt("quantidade");
                int tipo = rs.getInt("tipo");
                double gorjeta = (tipo == 2) ? valor*0.10 : (tipo == 3) ? valor*0.15 : 0;
                total += valor + gorjeta;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public double fecharConta(int numConta) throws ContaInexistente {
        double total = valorDaConta(numConta);
        try (Connection conn = getConexao()) {
            String sql = "UPDATE conta SET fechada = true WHERE numConta = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, numConta);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Override
    public void addCardapio(int num, String nome, double valItem, int tipo) throws ItemJaCadastrado, DadosInvalidos {
        if (num <=0 || valItem <=0 || nome == null || nome.trim().isEmpty() || tipo < 1 || tipo >3)
            throw new DadosInvalidos();
        try (Connection conn = getConexao()) {
            String sqlCheck = "SELECT * FROM item WHERE numero = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, num);
            ResultSet rs = psCheck.executeQuery();
            if (rs.next()) throw new ItemJaCadastrado();

            String sqlInsert = "INSERT INTO item (numero, nome, valor, tipo) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
            psInsert.setInt(1, num);
            psInsert.setString(2, nome);
            psInsert.setDouble(3, valItem);
            psInsert.setInt(4, tipo);
            psInsert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrarPagamento(int numConta, double val) throws PagamentoMaior, ContaInexistente, DadosInvalidos, ContaNaoFechada {
        if (numConta <=0 || val <=0) throw new DadosInvalidos();
        try (Connection conn = getConexao()) {
            verificarContaExiste(numConta);

            // verifica se conta está fechada
            String sqlFechada = "SELECT fechada, pagamentos FROM conta WHERE numConta = ?";
            PreparedStatement ps = conn.prepareStatement(sqlFechada);
            ps.setInt(1, numConta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (!rs.getBoolean("fechada")) throw new ContaNaoFechada();
                double totalPagamentos = rs.getDouble("pagamentos") + val;
                double totalConta = valorDaConta(numConta);
                if (totalPagamentos > totalConta) throw new PagamentoMaior();

                // atualiza pagamento
                String sqlUpdate = "UPDATE conta SET pagamentos = pagamentos + ? WHERE numConta = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setDouble(1, val);
                psUpdate.setInt(2, numConta);
                psUpdate.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Consumo> extratoDeConta(int numConta) throws ContaInexistente {
        ArrayList<Consumo> extrato = new ArrayList<>();
        try (Connection conn = getConexao()) {
            verificarContaExiste(numConta);

            String sql = "SELECT c.quantidade, i.valor, i.tipo, i.nome FROM consumo c JOIN item i ON c.numItem = i.numero WHERE c.numConta = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, numConta);
            ResultSet rs = ps.executeQuery();

            double totalGeral = 0;
            double gorjetaTotal = 0;

            while (rs.next()) {
                int qtd = rs.getInt("quantidade");
                double valorUnit = rs.getDouble("valor");
                int tipo = rs.getInt("tipo");
                String nome = rs.getString("nome");
                double valor = valorUnit * qtd;
                double gorjeta = (tipo == 2) ? valor*0.10 : (tipo == 3) ? valor*0.15 : 0;

                totalGeral += valor;
                gorjetaTotal += gorjeta;

                Item item = new Item(0, nome, valorUnit, tipo); // id irrelevante aqui
                extrato.add(new Consumo(item, qtd));

                System.out.println(nome + " x" + qtd + " Valor unitário: R$" + valorUnit + " Total: R$" + valor);
            }

            if (gorjetaTotal > 0) {
                System.out.println("Gorjeta do garçom (00) : R$" + gorjetaTotal);
            }

            double totalConta = totalGeral + gorjetaTotal;

            // pega pagamentos feitos
            String sqlPag = "SELECT pagamentos FROM conta WHERE numConta = ?";
            PreparedStatement psPag = conn.prepareStatement(sqlPag);
            psPag.setInt(1, numConta);
            ResultSet rsPag = psPag.executeQuery();
            double pagos = 0;
            if (rsPag.next()) pagos = rsPag.getDouble("pagamentos");

            System.out.println("TOTAL GERAL: R$" + totalConta);
            System.out.println("Pagamentos já feitos: R$" + pagos);
            System.out.println("Valor restante: R$" + (totalConta - pagos));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return extrato;
    }

    public void listarCardapio() {
        try (Connection conn = getConexao()) {
            String sql = "SELECT * FROM item";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("===== CARDÁPIO =====");
            while (rs.next()) {
                int numero = rs.getInt("numero");
                String nome = rs.getString("nome");
                double valor = rs.getDouble("valor");
                int tipoInt = rs.getInt("tipo");
                String tipo = (tipoInt==1)?"Ingresso":(tipoInt==2?"Bebida":"Comida");
                System.out.println(numero + " - " + nome + " R$" + valor + " [" + tipo + "]");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
