package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.ItemCardapio;
import com.example.demo.yasmimJose.repository.ItemCardapioRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository repo;

    public ItemCardapioService(ItemCardapioRepository repo) { this.repo = repo; }

    public ItemCardapio criar(ItemCardapio item) {
        return repo.save(item);
    }

    public List<ItemCardapio> listar() { return repo.findAll(); }

    public ItemCardapio buscar(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Item não encontrado")); }

    public void excluir(Long id) { repo.deleteById(id); }
}
