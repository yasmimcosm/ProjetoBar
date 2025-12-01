package com.example.demo.yasmimJose.dto;

public class AbrirMesaDTO {
    private int numeroMesa;
    private int numeroPessoas;
    private boolean pagarCouvert;
    private boolean couvertIndividual;
    private double valorCouvert;
    private double percentualGorjetaComida;
    private double percentualGorjetaBebida;
    private double total;
    private double totalPago;
    private boolean contaFechada;

    // Getters e setters
    public int getNumeroMesa() { return numeroMesa; }
    public void setNumeroMesa(int numeroMesa) { this.numeroMesa = numeroMesa; }

    public int getNumeroPessoas() { return numeroPessoas; }
    public void setNumeroPessoas(int numeroPessoas) { this.numeroPessoas = numeroPessoas; }

    public boolean isPagarCouvert() { return pagarCouvert; }
    public void setPagarCouvert(boolean pagarCouvert) { this.pagarCouvert = pagarCouvert; }

    public boolean isCouvertIndividual() { return couvertIndividual; }
    public void setCouvertIndividual(boolean couvertIndividual) { this.couvertIndividual = couvertIndividual; }

    public double getValorCouvert() { return valorCouvert; }
    public void setValorCouvert(double valorCouvert) { this.valorCouvert = valorCouvert; }

    public double getPercentualGorjetaComida() { return percentualGorjetaComida; }
    public void setPercentualGorjetaComida(double percentualGorjetaComida) { this.percentualGorjetaComida = percentualGorjetaComida; }

    public double getPercentualGorjetaBebida() { return percentualGorjetaBebida; }
    public void setPercentualGorjetaBebida(double percentualGorjetaBebida) { this.percentualGorjetaBebida = percentualGorjetaBebida; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public double getTotalPago() { return totalPago; }
    public void setTotalPago(double totalPago) { this.totalPago = totalPago; }

    public boolean isContaFechada() { return contaFechada; }
    public void setContaFechada(boolean contaFechada) { this.contaFechada = contaFechada; }
}
