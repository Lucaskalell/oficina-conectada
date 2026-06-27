package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.entity.Agendamento;
import io.github.lucaskalell.oficinaconectada.service.AgendamentoService;
import io.github.lucaskalell.oficinaconectada.status.StatusAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<Agendamento>> listarTodos() {
        return ResponseEntity.ok(agendamentoService.listarTodos());
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Agendamento>> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim
    ) {
        return ResponseEntity.ok(agendamentoService.listarPorPeriodo(inicio, fim));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agendamento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(agendamentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Agendamento> criar(@RequestBody Map<String, Object> body) {
        Long clienteId = Long.valueOf(body.get("clienteId").toString());
        Long carroId = Long.valueOf(body.get("carroId").toString());
        Long mecanicoId = body.get("mecanicoId") != null ? Long.valueOf(body.get("mecanicoId").toString()) : null;
        LocalDateTime dataHora = LocalDateTime.parse(body.get("dataHora").toString());
        String descricaoServico = body.get("descricaoServico").toString();

        Agendamento criado = agendamentoService.criar(clienteId, carroId, mecanicoId, dataHora, descricaoServico);
        return ResponseEntity.created(URI.create("/agendamentos/" + criado.getId())).body(criado);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Agendamento> atualizarStatus(
            @PathVariable Long id,
            @RequestParam StatusAgendamento status
    ) {
        return ResponseEntity.ok(agendamentoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        agendamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
