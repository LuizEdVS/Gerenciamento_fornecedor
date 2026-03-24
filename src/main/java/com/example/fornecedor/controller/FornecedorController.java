package com.example.fornecedor.controller;

import com.example.fornecedor.entity.Fornecedor;
import com.example.fornecedor.service.FornecedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @PostMapping
    public ResponseEntity<Fornecedor> criar(@RequestBody Fornecedor fornecedor) {
        Fornecedor criado = fornecedorService.criar(fornecedor);
        URI location = URI.create("/fornecedores/" + criado.getId());
        return ResponseEntity.created(location).body(criado);
    }

    @GetMapping
    public ResponseEntity<List<Fornecedor>> listarTodos() {
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> buscarPorId(@PathVariable Long id) {
        return fornecedorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPorId(@PathVariable Long id) {
        boolean deletado = fornecedorService.deletarPorId(id);
        if (!deletado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.noContent().build();
    }
}