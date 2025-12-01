package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Conta;
import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.model.Pedido;
import com.example.demo.yasmimJose.repository.ContaRepository;
import com.example.demo.yasmimJose.repository.MesaRepository;
import com.example.demo.yasmimJose.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GarcomService {

    private final ContaRepository contaRepo;
    private final MesaRepository mesaRepo;
    private final PedidoRepository pedidoRepo;

    public GarcomService(ContaRepository contaRepo, MesaRepository mesaRepo, PedidoRepository pedidoRepo) {
        this.contaRepo = contaRepo;
        this.mesaRepo = mesaRepo;
        this.pedidoRepo = pedidoRepo;
    }

    // ---------------- Abrir mesa pelo número ----------------
    public Conta abrirMesa(int numeroMesa, int numeroPessoas) {
        Mesa mesa = mesaRepo.findByNumero(numeroMesa)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        if (!mesa.getDisponivel()) {
            throw new RuntimeException("Mesa já está ocupada");
        }

        // Marca mesa como ocupada
        mesa.setDisponivel(false);
        mesaRepo.save(mesa);

        // Cria conta com os campos obrigatórios da nova tabela
        Conta conta = new Conta(mesa, numeroPessoas);
        conta.setTotal(0.0);
        conta.setTotalPago(0.0);
        conta.setContaFechada(false);

        return contaRepo.save(conta);
    }

    // ---------------- Listar contas abertas ----------------
    public List<Conta> listarContasAbertas() {
        return contaRepo.findAll().stream()
                .filter(c -> !c.isContaFechada())
                .toList();
    }

    // ---------------- Adicionar pedido ----------------
    public Pedido adicionarPedido(Long contaId, Pedido pedido) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.adicionarPedido(pedido);
        pedido.setConta(conta);

        pedidoRepo.save(pedido);
        return contaRepo.save(conta).getPedidos().stream()
                .filter(p -> p.getId().equals(pedido.getId()))
                .findFirst()
                .orElse(pedido);
    }

    // ---------------- Remover pedido ----------------
    public void removerPedido(Long contaId, Long pedidoId) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        conta.removerPedido(pedido);
        pedidoRepo.delete(pedido);
        contaRepo.save(conta);
    }

    // ---------------- Registrar pagamento ----------------
    public Conta registrarPagamento(Long contaId, double valor) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setTotalPago(conta.getTotalPago() + valor);
        conta.setTotal(conta.getTotal() - valor);

        return contaRepo.save(conta);
    }

    // ---------------- Fechar conta ----------------
    public Conta fecharConta(Long contaId) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setContaFechada(true);
        conta.setDataFechamento(java.time.LocalDateTime.now());

        Mesa mesa = conta.getMesa();
        if (mesa != null) {
            mesa.setDisponivel(true);
            mesaRepo.save(mesa);
        }

        return contaRepo.save(conta);
    }

    public void cancelarPedido(Long contaId, Long pedidoId, String motivo) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        Pedido pedido = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // registra motivo do cancelamento
        pedido.setMotivoCancelamento(motivo);

        // atualiza total da conta
        double valorTotal = pedido.getPreco() * pedido.getQuantidade();
        conta.setTotal(conta.getTotal() - valorTotal);
        if (conta.getTotal() < 0) conta.setTotal(0);

        // remove pedido
        conta.getPedidos().remove(pedido);
        pedidoRepo.delete(pedido);
        contaRepo.save(conta);
    }

}
