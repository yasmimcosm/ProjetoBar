package com.example.demo.yasmimJose.service;

import com.example.demo.yasmimJose.model.Item;
import com.example.demo.yasmimJose.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repo;

    public ItemService(ItemRepository repo) { this.repo = repo; }

    public Item salvar(Item item) {
        return repo.save(item);
    }

    public List<Item> listar() {
        return repo.findAll();
    }

    public Item buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }

    public Item editar(Long id, Item item) {
        Item existente = buscar(id);
        existente.setNome(item.getNome());
        existente.setCategoria(item.getCategoria());
        existente.setPreco(item.getPreco());
        existente.setTipo(item.getTipo());
        return repo.save(existente);
    }

    public void excluir(Long id) {
        Item item = buscar(id);
        repo.delete(item);
    }
}
