package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.CarroDTO;
import io.github.lucaskalell.oficinaconectada.dto.CarroStatusDTO;
import io.github.lucaskalell.oficinaconectada.service.CarroService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carros")
@RequiredArgsConstructor
public class CarroController {

    private final CarroService carroService;

    @GetMapping
    public ResponseEntity<List<CarroDTO>> listarTodos() {
        return ResponseEntity.ok(carroService.listarTodos());
    }

    @GetMapping("/status")
    public ResponseEntity<List<CarroStatusDTO>> listarComStatus() {
        return ResponseEntity.ok(carroService.listarComStatusDeServico());
    }

    @PostMapping
    public ResponseEntity<CarroDTO> criar(@RequestBody CarroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> atualizar(@PathVariable Long id, @RequestBody CarroDTO dto) {
        return ResponseEntity.ok(carroService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        carroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}