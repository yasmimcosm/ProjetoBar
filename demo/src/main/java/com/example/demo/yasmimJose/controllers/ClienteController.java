package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Conta;
import com.example.demo.yasmimJose.model.Pedido;
import com.example.demo.yasmimJose.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    private final ContaService contaService;

    public ClienteController(ContaService contaService) {
        this.contaService = contaService;
    }

    // ---------------- Ver conta ----------------
    @GetMapping("/conta/{id}")
    public ResponseEntity<Conta> verConta(@PathVariable Long id) {
        Conta conta = contaService.getConta(id);
        return ResponseEntity.ok(conta);
    }

    // ---------------- Adicionar pedido ----------------
    @PostMapping("/conta/{id}/pedido")
    public ResponseEntity<Conta> adicionarPedido(
            @PathVariable Long id,
            @RequestBody Pedido pedido) {

        Conta conta = contaService.getConta(id);
        conta.adicionarPedido(pedido);
        contaService.salvar(conta);  // salva no banco
        return ResponseEntity.ok(conta);
    }

    // ---------------- Remover pedido ----------------
    @DeleteMapping("/conta/{id}/pedido/{pedidoId}")
    public ResponseEntity<Conta> removerPedido(
            @PathVariable Long id,
            @PathVariable Long pedidoId) {

        Conta conta = contaService.getConta(id);
        Pedido pedido = conta.getPedidos().stream()
                .filter(p -> p.getId().equals(pedidoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        conta.removerPedido(pedido);
        contaService.salvar(conta);
        return ResponseEntity.ok(conta);
    }

    // ---------------- Registrar pagamento ----------------
    @PatchMapping("/conta/{id}/pagamento")
    public ResponseEntity<Conta> registrarPagamento(
            @PathVariable Long id,
            @RequestParam double valor) {

        Conta conta = contaService.getConta(id);
        conta.registrarPagamento(valor);
        contaService.salvar(conta);
        return ResponseEntity.ok(conta);
    }

    // ---------------- Fechar conta ----------------
    @PatchMapping("/conta/{id}/fechar")
    public ResponseEntity<Conta> fecharConta(@PathVariable Long id) {
        Conta conta = contaService.getConta(id);
        conta.fecharConta();
        contaService.salvar(conta);
        return ResponseEntity.ok(conta);
    }
}
