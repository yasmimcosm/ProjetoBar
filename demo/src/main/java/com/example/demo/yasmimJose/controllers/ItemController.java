package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.model.Item;
import com.example.demo.yasmimJose.service.ItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cardapio")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> listar() {
        return service.listar();
    }

    @PostMapping
    public Item criar(@RequestBody Item item) {
        return service.salvar(item);
    }

    @PutMapping("/{id}")
    public Item editar(@PathVariable Long id, @RequestBody Item item) {
        return service.editar(id, item);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }
}
