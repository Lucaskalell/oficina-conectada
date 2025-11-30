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
    public ResponseEntity<List<Venda>> getTodasVendas() {
        List<Venda> vendas = vendaService.listarTodasVendas();
        return ResponseEntity.ok(vendas);
    }

    @PostMapping
    public ResponseEntity<Venda> criarVenda(@RequestBody VendaRequestDTO vendaRequest) {
        Venda novaVenda = vendaService.criarVenda(vendaRequest);
        return ResponseEntity.created(URI.create("/vendas/" + novaVenda.getId())).body(novaVenda);
    }
}
