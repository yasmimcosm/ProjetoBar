package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.AbrirMesaDTO;
import com.example.demo.yasmimJose.model.Conta;
import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.repository.ContaRepository;
import com.example.demo.yasmimJose.repository.MesaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/garcom")
@CrossOrigin(origins = "*")
public class GarcomController {

    private final ContaRepository contaRepo;
    private final MesaRepository mesaRepo;

    public GarcomController(ContaRepository contaRepo, MesaRepository mesaRepo) {
        this.contaRepo = contaRepo;
        this.mesaRepo = mesaRepo;
    }

    @PostMapping("/abrirMesa")
    public ResponseEntity<Conta> abrirMesa(@RequestBody AbrirMesaDTO dto) {

        Mesa mesa = mesaRepo.findByNumero(dto.getNumeroMesa())
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        if (!mesa.getDisponivel()) {
            throw new RuntimeException("Mesa já está ocupada");
        }

        // Cria a conta e preenche apenas os campos da nova tabela
        Conta conta = new Conta();
        conta.setMesa(mesa);
        conta.setNumeroPessoas(dto.getNumeroPessoas());
        conta.setTotal(0.0);
        conta.setTotalPago(0.0);
        conta.setContaFechada(false);
        conta.setDataAbertura(LocalDateTime.now());

        // Marca a mesa como ocupada
        mesa.setDisponivel(false);
        mesaRepo.save(mesa);

        // Salva a conta
        Conta novaConta = contaRepo.save(conta);

        return ResponseEntity.ok(novaConta);
    }
}
