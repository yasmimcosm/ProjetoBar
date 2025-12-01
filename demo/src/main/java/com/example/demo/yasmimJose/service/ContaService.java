package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Conta;
import com.example.demo.yasmimJose.model.Pedido;
import com.example.demo.yasmimJose.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    // ---------------- Get conta por ID ----------------
    public Conta getConta(Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    // ---------------- Salvar ou atualizar conta ----------------
    public Conta salvar(Conta conta) {
        return contaRepository.save(conta);
    }

    // ---------------- Pedidos ----------------
    public List<Pedido> listarPedidos(Long contaId) {
        Conta conta = getConta(contaId);
        return conta.getPedidos();
    }

    public Pedido adicionarPedido(Long contaId, Pedido pedido) {
        Conta conta = getConta(contaId);
        conta.adicionarPedido(pedido);
        salvar(conta);
        return pedido;
    }

    public void removerPedido(Long contaId, Long pedidoId) {
        Conta conta = getConta(contaId);
        Pedido pedido = conta.getPedidos().stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        conta.removerPedido(pedido);
        salvar(conta);
    }

    // ---------------- Cancelar pedido com motivo ----------------
    public void cancelarPedido(Long contaId, Long pedidoId, String motivo) {
        Conta conta = getConta(contaId);
        Pedido pedido = conta.getPedidos().stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // registra motivo de cancelamento
        pedido.setMotivoCancelamento(motivo);

        // opcional: atualizar total da conta
        double valorTotal = pedido.getPreco() * pedido.getQuantidade();
        conta.setTotal(Math.max(conta.getTotal() - valorTotal, 0));

        // remove pedido da lista ativa (pode manter histórico se quiser)
        conta.removerPedido(pedido);
        salvar(conta);
    }

    // ---------------- Pagamento ----------------
    public void registrarPagamento(Long contaId, double valor) {
        Conta conta = getConta(contaId);
        conta.registrarPagamento(valor);
        salvar(conta);
    }

    // ---------------- Fechar conta ----------------
    public void fecharConta(Long contaId) {
        Conta conta = getConta(contaId);
        conta.fecharConta();
        salvar(conta);
    }
}
