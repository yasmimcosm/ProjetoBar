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

    private LocalDateTime abertoEm;
    private LocalDateTime fechadoEm;

    private boolean couvertAtivo;
    private Double couvertValor = 0.0;

    private Double gorjetaPercentual = 0.0; // ex: 10.0 para 10%
    private Double total = 0.0;

    private String status; // ABERTA, FECHADA

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    @OneToMany(mappedBy = "conta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public Conta() {}
    public Conta(Mesa mesa) {
        this.mesa = mesa;
        this.abertoEm = LocalDateTime.now();
        this.status = "ABERTA";
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Mesa getMesa() { return mesa; }
    public void setMesa(Mesa mesa) { this.mesa = mesa; }
    public LocalDateTime getAbertoEm() { return abertoEm; }
    public void setAbertoEm(LocalDateTime abertoEm) { this.abertoEm = abertoEm; }
    public LocalDateTime getFechadoEm() { return fechadoEm; }
    public void setFechadoEm(LocalDateTime fechadoEm) { this.fechadoEm = fechadoEm; }
    public boolean isCouvertAtivo() { return couvertAtivo; }
    public void setCouvertAtivo(boolean couvertAtivo) { this.couvertAtivo = couvertAtivo; }
    public Double getCouvertValor() { return couvertValor; }
    public void setCouvertValor(Double couvertValor) { this.couvertValor = couvertValor; }
    public Double getGorjetaPercentual() { return gorjetaPercentual; }
    public void setGorjetaPercentual(Double gorjetaPercentual) { this.gorjetaPercentual = gorjetaPercentual; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Pedido> getPedidos() { return pedidos; }
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }
    public List<Pagamento> getPagamentos() { return pagamentos; }
    public void setPagamentos(List<Pagamento> pagamentos) { this.pagamentos = pagamentos; }
}

