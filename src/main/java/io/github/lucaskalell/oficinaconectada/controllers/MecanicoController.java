package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.entity.Mecanico;
import io.github.lucaskalell.oficinaconectada.service.MecanicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/mecanicos")
@RequiredArgsConstructor
public class MecanicoController {

    private final MecanicoService mecanicoService;

    @GetMapping
    public ResponseEntity<List<Mecanico>> listarAtivos() {
        return ResponseEntity.ok(mecanicoService.listarAtivos());
    }

    @GetMapping("/todos")
    public ResponseEntity<List<Mecanico>> listarTodos() {
        return ResponseEntity.ok(mecanicoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mecanicoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Mecanico> criar(@RequestBody Mecanico mecanico) {
        Mecanico criado = mecanicoService.criar(mecanico);
        return ResponseEntity.created(URI.create("/mecanicos/" + criado.getId())).body(criado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> atualizar(@PathVariable Long id, @RequestBody Mecanico dados) {
        return ResponseEntity.ok(mecanicoService.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativar(@PathVariable Long id) {
        mecanicoService.desativar(id);
        return ResponseEntity.noContent().build();
    }
}
