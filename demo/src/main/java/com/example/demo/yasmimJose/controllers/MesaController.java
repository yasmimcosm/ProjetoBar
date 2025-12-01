package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.service.MesaService;
import com.example.demo.yasmimJose.dto.MesaUpdateDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/mesas")
public class MesaController {

    private final MesaService mesaService;

    public MesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    // Listar todas as mesas
    @GetMapping
    public ResponseEntity<List<Mesa>> listarMesas() {
        return ResponseEntity.ok(mesaService.listar());
    }

    // Criar nova mesa
    @PostMapping
    public ResponseEntity<Mesa> criarMesa(@RequestBody Mesa mesa) {
        mesa.setDisponivel(true); // mesa nova começa disponível
        return ResponseEntity.ok(mesaService.criar(mesa));
    }

    // Atualizar qualquer campo da mesa
    @PutMapping("/{id}")
    public ResponseEntity<Mesa> atualizarMesa(
            @PathVariable Long id,
            @RequestBody MesaUpdateDTO dto) {

        Mesa mesa = mesaService.buscar(id);

        if(dto.getNumero() != null) mesa.setNumero(dto.getNumero());
        if(dto.getCapacidade() != null) mesa.setCapacidade(dto.getCapacidade());
        if(dto.getDisponivel() != null) mesa.setDisponivel(dto.getDisponivel());

        Mesa mesaAtualizada = mesaService.atualizar(mesa);
        return ResponseEntity.ok(mesaAtualizada);
    }

    // Alterar disponibilidade rápido
    @PatchMapping("/{id}/disponivel")
    public ResponseEntity<Mesa> alterarDisponibilidade(@PathVariable Long id, @RequestParam boolean disponivel) {
        Mesa mesa = mesaService.buscar(id);
        mesa.setDisponivel(disponivel);
        return ResponseEntity.ok(mesaService.atualizar(mesa));
    }

    // Excluir mesa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMesa(@PathVariable Long id) {
        Mesa mesa = mesaService.buscar(id);
        mesaService.deletar(mesa);
        return ResponseEntity.noContent().build();
    }
}
