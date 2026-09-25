package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.entity.HistoricoStatusOs;
import io.github.lucaskalell.oficinaconectada.service.HistoricoStatusOsService;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordens/{ordemId}/historico")
@RequiredArgsConstructor
public class HistoricoStatusOsController {

    private final HistoricoStatusOsService historicoStatusOsService;

    @GetMapping
    public ResponseEntity<List<HistoricoStatusOs>> listar(@PathVariable Long ordemId) {
        return ResponseEntity.ok(historicoStatusOsService.listarPorOrdem(ordemId));
    }

    @PostMapping
    public ResponseEntity<Void> registrar(
            @PathVariable Long ordemId,
            @RequestBody Map<String, Object> body
    ) {
        StatusOrdemDeServico novoStatus = StatusOrdemDeServico.valueOf(body.get("status").toString());
        Long usuarioId = body.get("usuarioId") != null ? Long.valueOf(body.get("usuarioId").toString()) : null;
        String observacao = body.get("observacao") != null ? body.get("observacao").toString() : null;

        historicoStatusOsService.registrarMudancaStatus(ordemId, novoStatus, usuarioId, observacao);
        return ResponseEntity.noContent().build();
    }
}
