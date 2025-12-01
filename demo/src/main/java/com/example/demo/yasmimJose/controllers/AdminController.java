package com.example.demo.yasmimJose.controllers;

import com.example.demo.yasmimJose.dto.ItemDTO;
import com.example.demo.yasmimJose.model.Item;
import com.example.demo.yasmimJose.service.ItemService;
import com.example.demo.yasmimJose.service.MesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminController {

    private final ItemService itemService;
    private final MesaService mesaService;

    public AdminController(ItemService itemService, MesaService mesaService) {
        this.itemService = itemService;
        this.mesaService = mesaService;
    }

    // ------------------------------
    //           CARDÁPIO
    // ------------------------------

    @PostMapping("/item")
    public ResponseEntity<Item> criarItem(@RequestBody ItemDTO dto) {
        Item item = new Item(dto.getNome(), dto.getCategoria(), dto.getPreco(), dto.getTipo());
        return ResponseEntity.ok(itemService.salvar(item));
    }

    @GetMapping("/itens")
    public ResponseEntity<List<Item>> listarItens() {
        return ResponseEntity.ok(itemService.listar());
    }

    @PutMapping("/item/{id}")
    public ResponseEntity<Item> editarItem(@PathVariable Long id, @RequestBody ItemDTO dto) {
        Item itemAtualizado = new Item(dto.getNome(), dto.getCategoria(), dto.getPreco(), dto.getTipo());
        return ResponseEntity.ok(itemService.editar(id, itemAtualizado));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<Void> excluirItem(@PathVariable Long id) {
        itemService.excluir(id);
        return ResponseEntity.ok().build();
    }

    // ------------------------------
    //             MESAS (opcional)
    // ------------------------------
    // Mantemos apenas GET/POST administrativas caso queira
    // sem conflito com o MesaController público
    @GetMapping("/mesas/admin")
    public ResponseEntity<List<?>> listarMesasAdmin() {
        return ResponseEntity.ok(mesaService.listar());
    }
}
