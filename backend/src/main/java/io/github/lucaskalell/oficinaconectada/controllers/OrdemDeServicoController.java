package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.service.OrdemDeServicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemDeServicoController {

    private final OrdemDeServicoService ordemDeServicoService;

    @GetMapping("/resumo")
    public ResponseEntity<List<OrdemDeServicoDTO>> listarResumo() {
        return ResponseEntity.ok(ordemDeServicoService.listarResumo());
    }

    @PostMapping("/criar")
    public ResponseEntity<OrdemDeServicoDTO> criar(@RequestBody CriarOrdemDeServicoRequest requisicao) {
        OrdemDeServicoDTO novaOrdem = ordemDeServicoService.criar(requisicao);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaOrdem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        ordemDeServicoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/fotos")
    public ResponseEntity<String> uploadFoto(
            @PathVariable Long id,
            @RequestParam("foto") MultipartFile foto
    ) {
        ordemDeServicoService.salvarFoto(id, foto);
        return ResponseEntity.ok("Foto salva com sucesso");
    }
}