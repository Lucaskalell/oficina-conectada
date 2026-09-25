package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.ClienteCarroRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.ClienteDTO;
import io.github.lucaskalell.oficinaconectada.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping("/completo")
    public ResponseEntity<ClienteDTO> criarComCarro(@RequestBody ClienteCarroRequestDTO dto) {
        ClienteDTO novoCliente = clienteService.criarClienteComCarro(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@RequestBody ClienteDTO clienteDTO) {
        ClienteDTO novoCliente = clienteService.criarCliente(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodosClientes());
    }

    @GetMapping("/com-carros")
    public ResponseEntity<List<ClienteDTO>> listarComCarros() {
        return ResponseEntity.ok(clienteService.listarTodosClientesComCarro());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClientePorId(id));
    }

    @GetMapping("/{id}/completo")
    public ResponseEntity<ClienteDTO> buscarCompletoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.buscarClienteECarroCompletoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Long id, @RequestBody ClienteDTO clienteAtualizado) {
        return ResponseEntity.ok(clienteService.atualizarCliente(id, clienteAtualizado));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}