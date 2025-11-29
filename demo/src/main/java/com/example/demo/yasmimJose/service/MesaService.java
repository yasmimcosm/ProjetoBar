package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.repository.MesaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MesaService {
    private final MesaRepository repo;
    public MesaService(MesaRepository repo) { this.repo = repo; }
    public Mesa criar(Mesa m) { return repo.save(m); }
    public List<Mesa> listar() { return repo.findAll(); }
    public Mesa buscar(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Mesa não encontrada")); }
}
