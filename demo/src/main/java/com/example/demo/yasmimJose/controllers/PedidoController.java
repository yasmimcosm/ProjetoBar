package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Pedido;
import com.example.demo.yasmimJose.service.ContaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conta")
@CrossOrigin(origins = "*")
public class PedidoController {

    private final ContaService service;

    public PedidoController(ContaService service) {
        this.service = service;
    }

    // ---------------- Listar pedidos da conta ----------------
    @GetMapping("/{contaId}/pedidos")
    public List<Pedido> listar(@PathVariable Long contaId) {
        return service.listarPedidos(contaId);
    }

    // ---------------- Adicionar pedido ----------------
    @PostMapping("/{contaId}/pedidos")
    public Pedido adicionar(@PathVariable Long contaId, @RequestBody Pedido pedido) {
        return service.adicionarPedido(contaId, pedido);
    }

    // ---------------- Remover pedido (sem motivo) ----------------
    @DeleteMapping("/{contaId}/pedidos/{pedidoId}")
    public void remover(@PathVariable Long contaId, @PathVariable Long pedidoId) {
        service.removerPedido(contaId, pedidoId);
    }

    // ---------------- Cancelar pedido (com motivo) ----------------
    @PatchMapping("/{contaId}/pedidos/{pedidoId}/cancelar")
    public void cancelar(
            @PathVariable Long contaId,
            @PathVariable Long pedidoId,
            @RequestParam String motivo) {
        service.cancelarPedido(contaId, pedidoId, motivo);
    }
}
