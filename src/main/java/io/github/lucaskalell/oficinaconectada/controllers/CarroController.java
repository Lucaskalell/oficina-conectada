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
    public ResponseEntity<List<CarroDTO>> findAll() {
        return ResponseEntity.ok(carroService.todosOsCarros());
    }

    @GetMapping("/status")
    public ResponseEntity<List<CarroStatusDTO>> todosComStatus() {
        return ResponseEntity.ok(carroService.carrosComStatusDeServico());
    }

    @PostMapping
    public ResponseEntity<CarroDTO> criarCarro(@RequestBody CarroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carroService.criarOCarro(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroDTO> atualizarCarro(@PathVariable Long id, @RequestBody CarroDTO dto) {
        return ResponseEntity.ok(carroService.atualizarOCarro(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCarro(@PathVariable Long id) {
        carroService.deletarOCarro(id);
        return ResponseEntity.noContent().build();
    }
}
