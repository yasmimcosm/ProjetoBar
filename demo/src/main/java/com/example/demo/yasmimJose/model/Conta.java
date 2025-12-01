package com.example.demo.yasmimJose.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conta")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mesa mesa;

    @Column(name = "numero_pessoas", nullable = false)
    private int numeroPessoas;

    @Column(name = "pagar_couvert", nullable = false)
    private boolean pagarCouvert = false;

    @Column(name = "couvert_individual", nullable = false)
    private boolean couvertIndividual = false;

    @Column(name = "total", nullable = false)
    private double total = 0.0;

    @Column(name = "total_pago", nullable = false)
    private double totalPago = 0.0;

    @Column(name = "conta_fechada", nullable = false)
    private boolean contaFechada = false;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Conta() {}

    // Construtor simplificado para abrir mesa
    public Conta(Mesa mesa, int numeroPessoas) {
        this.mesa = mesa;
        this.numeroPessoas = numeroPessoas;
        this.dataAbertura = LocalDateTime.now();
        this.contaFechada = false;
        this.total = 0.0;
    }

    // ---------------- Getters e Setters ----------------
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }

    public int getNumeroPessoas() { return numeroPessoas; }
    public void setNumeroPessoas(int numeroPessoas) { this.numeroPessoas = numeroPessoas; }

    public boolean isPagarCouvert() { return pagarCouvert; }
    public void setPagarCouvert(boolean pagarCouvert) { this.pagarCouvert = pagarCouvert; }

    public boolean isCouvertIndividual() { return couvertIndividual; }
    public void setCouvertIndividual(boolean couvertIndividual) { this.couvertIndividual = couvertIndividual; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getTotalPago() { return totalPago; }
    public void setTotalPago(double totalPago) { this.totalPago = totalPago; }

    public boolean isContaFechada() { return contaFechada; }
    public void setContaFechada(boolean contaFechada) { this.contaFechada = contaFechada; }

    public LocalDateTime getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(LocalDateTime dataAbertura) { this.dataAbertura = dataAbertura; }

    public LocalDateTime getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(LocalDateTime dataFechamento) { this.dataFechamento = dataFechamento; }

    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

    public List<Pagamento> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<Pagamento> pagamentos) { this.pagamentos = pagamentos; }

    // ---------------- Métodos de conveniência ----------------
    public void adicionarPedido(Pedido pedido) {
        if (pedido.getQuantidade() <= 0) return;
        pedidos.add(pedido);
        pedido.setConta(this);
        total += pedido.getPreco() * pedido.getQuantidade();
    }

    public void removerPedido(Pedido pedido) {
        if (pedidos.remove(pedido)) {
            total -= pedido.getPreco() * pedido.getQuantidade();
            pedido.setConta(null);
        }
    }

    public void registrarPagamento(double valor) {
        if (valor <= 0) return;
        totalPago += valor;
        total -= valor;
        if (total < 0) total = 0.0;
    }

    public void fecharConta() {
        this.contaFechada = true;
        this.dataFechamento = LocalDateTime.now();
        if (mesa != null) {
            mesa.setDisponivel(true);
        }
    }
}
