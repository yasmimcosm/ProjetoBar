package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.*;
import com.example.demo.yasmimJose.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContaService {

    private final ContaRepository contaRepo;
    private final MesaRepository mesaRepo;
    private final ItemRepository itemRepo; // antes ItemCardapioRepository
    private final PedidoRepository pedidoRepo;
    private final PagamentoRepository pagamentoRepo;

    public ContaService(ContaRepository contaRepo, MesaRepository mesaRepo,
                        ItemRepository itemRepo, PedidoRepository pedidoRepo,
                        PagamentoRepository pagamentoRepo) {
        this.contaRepo = contaRepo;
        this.mesaRepo = mesaRepo;
        this.itemRepo = itemRepo;
        this.pedidoRepo = pedidoRepo;
        this.pagamentoRepo = pagamentoRepo;
    }

    @Transactional
    public Conta abrirConta(Long mesaId) {
        Mesa mesa = mesaRepo.findById(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        if (mesa.getDisponivel() != null && !mesa.getDisponivel()) {
            throw new RuntimeException("Mesa já ocupada");
        }

        Conta conta = new Conta(mesa);
        mesa.setDisponivel(false);

        contaRepo.save(conta);
        mesaRepo.save(mesa);

        return conta;
    }

    @Transactional
    public Pedido adicionarPedido(Long contaId, Long itemId, Integer quantidade) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        if (!"ABERTA".equals(conta.getStatus())) throw new RuntimeException("Conta não está aberta");

        Item item = itemRepo.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não existe"));

        if (quantidade == null || quantidade <= 0) quantidade = 1;

        Pedido p = new Pedido(conta, item, quantidade);
        conta.getPedidos().add(p);

        pedidoRepo.save(p);
        recalcularTotal(conta);
        contaRepo.save(conta);

        return p;
    }

    @Transactional
    public Pedido cancelarPedido(Long contaId, Long pedidoId, String justificativa) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        Pedido p = pedidoRepo.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não existe"));

        p.setStatus("CANCELADO");
        p.setJustificativaCancelamento(justificativa);

        pedidoRepo.save(p);
        recalcularTotal(conta);
        contaRepo.save(conta);

        return p;
    }

    @Transactional
    public Conta ativarCouvert(Long contaId, boolean ativo, Double valor) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        conta.setCouvertAtivo(ativo);
        conta.setCouvertValor(ativo ? (valor != null ? valor : conta.getCouvertValor()) : 0.0);

        recalcularTotal(conta);
        contaRepo.save(conta);

        return conta;
    }

    @Transactional
    public Conta definirGorjeta(Long contaId, Double percentual) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        conta.setGorjetaPercentual(percentual != null ? percentual : 0.0);
        recalcularTotal(conta);
        contaRepo.save(conta);

        return conta;
    }

    @Transactional
    public Conta fecharConta(Long contaId) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        if (!"ABERTA".equals(conta.getStatus()))
            throw new RuntimeException("Conta não está aberta");

        recalcularTotal(conta);
        conta.setFechadoEm(java.time.LocalDateTime.now());
        conta.setStatus("FECHADA");

        Mesa mesa = conta.getMesa();
        mesa.setDisponivel(true);
        mesaRepo.save(mesa);

        contaRepo.save(conta);
        return conta;
    }

    @Transactional
    public Pagamento registrarPagamento(Long contaId, Double valor, String tipo, boolean parcial) {
        Conta conta = contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));

        double saldo = calcularSaldoRestante(conta);
        if (valor <= 0) throw new RuntimeException("Valor inválido");

        if (!parcial && valor < saldo) parcial = true;

        Pagamento pag = new Pagamento(conta, valor, tipo, parcial);
        pagamentoRepo.save(pag);
        conta.getPagamentos().add(pag);
        contaRepo.save(conta);

        return pag;
    }

    @Transactional(readOnly = true)
    public Conta getConta(Long contaId) {
        return contaRepo.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não existe"));
    }

    // helpers
    private void recalcularTotal(Conta conta) {
        double somaItens = conta.getPedidos().stream()
                .filter(p -> "ATIVO".equals(p.getStatus()))
                .mapToDouble(Pedido::getSubtotal).sum();

        double couvert = conta.isCouvertAtivo() ? (conta.getCouvertValor() != null ? conta.getCouvertValor() : 0.0) : 0.0;
        double gorjeta = (conta.getGorjetaPercentual() != null ? conta.getGorjetaPercentual() : 0.0) * somaItens / 100.0;
        double total = somaItens + couvert + gorjeta;
        conta.setTotal(total);
    }

    @Transactional(readOnly = true)
    public double calcularSaldoRestante(Conta conta) {
        double pagos = conta.getPagamentos().stream().mapToDouble(Pagamento::getValor).sum();
        double saldo = conta.getTotal() - pagos;
        return saldo < 0 ? 0.0 : saldo;
    }
}
