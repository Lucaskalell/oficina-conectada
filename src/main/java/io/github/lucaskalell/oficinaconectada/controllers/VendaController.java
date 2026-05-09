package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.VendaRequestDTO;
import io.github.lucaskalell.oficinaconectada.entity.Venda;
import io.github.lucaskalell.oficinaconectada.service.VendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<Venda>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodasVendas());
    }

    @PostMapping
    public ResponseEntity<Venda> criar(@RequestBody VendaRequestDTO requisicao) {
        Venda novaVenda = vendaService.criarVenda(requisicao);
        return ResponseEntity.created(URI.create("/vendas/" + novaVenda.getId())).body(novaVenda);
    }
}