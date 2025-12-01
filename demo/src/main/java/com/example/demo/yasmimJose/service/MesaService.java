package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.repository.MesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesaService {

    private final MesaRepository repo;

    public MesaService(MesaRepository repo) {
        this.repo = repo;
    }

    // Criar nova mesa
    public Mesa criar(Mesa mesa) {
        return repo.save(mesa);
    }

    // Listar todas as mesas
    public List<Mesa> listar() {
        return repo.findAll();
    }

    // Buscar mesa por ID
    public Mesa buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));
    }

    // Atualizar mesa existente
    public Mesa atualizar(Mesa mesa) {
        // Garantir que a mesa existe
        Mesa existente = buscar(mesa.getId());

        // Atualizar campos
        existente.setNumero(mesa.getNumero());
        existente.setCapacidade(mesa.getCapacidade());
        existente.setDisponivel(mesa.getDisponivel());

        // Salvar no banco
        return repo.save(existente);
    }

    // Deletar mesa
    public void deletar(Mesa mesa) {
        repo.delete(mesa);
    }
}

