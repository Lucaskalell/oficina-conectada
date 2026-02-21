package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.CarroDTO;
import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.service.OrdemDeServicoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens")
public class OrdemDeServicoController {
    private final OrdemDeServicoService ordemDeServicoService;

    public OrdemDeServicoController(OrdemDeServicoService ordemDeServicoService) {
        this.ordemDeServicoService = ordemDeServicoService;
    }

    @GetMapping("/resumo")
    public ResponseEntity<List<OrdemDeServicoDTO>> getResumo() {
        return ResponseEntity.ok(ordemDeServicoService.listarResumo());
    }

    @PostMapping("/criarOrdemDeServico")
    public ResponseEntity<OrdemDeServicoDTO> criarOrdemDeServico(@RequestBody CriarOrdemDeServicoRequest request) {
        OrdemDeServicoDTO novaOrdem = ordemDeServicoService.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOrdem);
    }
}
