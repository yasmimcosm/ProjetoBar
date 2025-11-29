package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.ItemCardapioDTO;
import com.example.demo.yasmimJose.model.ItemCardapio;
import com.example.demo.yasmimJose.model.Mesa;
import com.example.demo.yasmimJose.service.ItemCardapioService;
import com.example.demo.yasmimJose.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final ItemCardapioService itemService;
    private final MesaService mesaService;

    public AdminController(ItemCardapioService itemService, MesaService mesaService) {
        this.itemService = itemService;
        this.mesaService = mesaService;
    }

    @PostMapping("/item")
    public ResponseEntity<?> criarItem(@RequestBody ItemCardapioDTO dto) {
        ItemCardapio item = new ItemCardapio(dto.nome, dto.categoria, dto.preco);
        item = itemService.criar(item);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/itens")
    public ResponseEntity<List<ItemCardapio>> listarItens() {
        return ResponseEntity.ok(itemService.listar());
    }

    @PostMapping("/mesa")
    public ResponseEntity<?> criarMesa(@RequestBody Mesa m) {
        return ResponseEntity.ok(mesaService.criar(m));
    }

    @GetMapping("/mesas")
    public ResponseEntity<?> listarMesas() {
        return ResponseEntity.ok(mesaService.listar());
    }
}
