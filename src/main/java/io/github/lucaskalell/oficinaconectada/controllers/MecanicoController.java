package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.MecanicoRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.MecanicoResponseDTO;
import io.github.lucaskalell.oficinaconectada.dto.MecanicoUpdateDTO;
import io.github.lucaskalell.oficinaconectada.service.MecanicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mecanicos")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicoService mecanicoService;

    @GetMapping
    public ResponseEntity<List<MecanicoResponseDTO>> listarAtivos() {
        return ResponseEntity.ok(mecanicoService.listarAtivos());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<MecanicoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(mecanicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mecanicoService.buscarPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<MecanicoResponseDTO> criar(@RequestBody MecanicoRequestDTO dados) {
        MecanicoResponseDTO criado = mecanicoService.criar(dados);
        return ResponseEntity.created(URI.create("/mecanicos/" + criado.getId())).body(criado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<MecanicoResponseDTO> atualizar(@PathVariable Long id, @RequestBody MecanicoUpdateDTO dados) {
        return ResponseEntity.ok(mecanicoService.atualizar(id, dados));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        mecanicoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
