package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.*;
import com.example.demo.yasmimJose.model.*;
import com.example.demo.yasmimJose.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/garcom")
public class GarcomController {

    private final ContaService contaService;
    private final ItemService itemService;

    public GarcomController(ContaService contaService, ItemService itemService) {
        this.contaService = contaService;
        this.itemService = itemService;
    }

    // Abrir conta para uma mesa
    @PostMapping("/conta")
    public ResponseEntity<?> abrirConta(@RequestBody AbrirContaDTO dto) {
        Conta c = contaService.abrirConta(dto.mesaId);
        return ResponseEntity.ok(c);
    }

    // Adicionar pedido à conta
    @PostMapping("/conta/{id}/pedido")
    public ResponseEntity<?> adicionarPedido(@PathVariable Long id, @RequestBody PedidoDTO dto) {
        Pedido p = contaService.adicionarPedido(id, dto.itemId, dto.quantidade);
        return ResponseEntity.ok(p);
    }

    // Cancelar pedido
    @PostMapping("/conta/{id}/pedido/{pedidoId}/cancelar")
    public ResponseEntity<?> cancelar(@PathVariable Long id, @PathVariable Long pedidoId,
                                      @RequestParam(required = false) String justificativa) {
        Pedido p = contaService.cancelarPedido(id, pedidoId, justificativa);
        return ResponseEntity.ok(p);
    }

    // Ativar ou desativar couvert
    @PutMapping("/conta/{id}/couvert")
    public ResponseEntity<?> setCouvert(@PathVariable Long id,
                                        @RequestParam boolean ativo,
                                        @RequestParam(required = false) Double valor) {
        Conta c = contaService.ativarCouvert(id, ativo, valor);
        return ResponseEntity.ok(c);
    }

    // Definir gorjeta
    @PutMapping("/conta/{id}/gorjeta")
    public ResponseEntity<?> setGorjeta(@PathVariable Long id, @RequestParam Double percentual) {
        Conta c = contaService.definirGorjeta(id, percentual);
        return ResponseEntity.ok(c);
    }

    // Fechar conta
    @PostMapping("/conta/{id}/fechar")
    public ResponseEntity<?> fechar(@PathVariable Long id) {
        Conta c = contaService.fecharConta(id);
        return ResponseEntity.ok(c);
    }

    // Registrar pagamento (parcial ou total)
    @PostMapping("/conta/{id}/pagamento")
    public ResponseEntity<?> pagamento(@PathVariable Long id, @RequestBody PagamentoDTO dto) {
        Pagamento p = contaService.registrarPagamento(id, dto.valor, dto.tipo,
                dto.parcial != null ? dto.parcial : false);
        return ResponseEntity.ok(p);
    }
}
