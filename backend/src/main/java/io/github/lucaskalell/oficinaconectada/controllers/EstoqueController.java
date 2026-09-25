package io.github.lucaskalell.oficinaconectada.controllers;

import io.github.lucaskalell.oficinaconectada.dto.EstoqueResumoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Categoria;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.SubCategoria;
import io.github.lucaskalell.oficinaconectada.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {
    private final EstoqueService estoqueService;

    @GetMapping("/resumo")
    public ResponseEntity<EstoqueResumoDTO> obterResumo() {
        return ResponseEntity.ok(estoqueService.getEstoqueResumo());
    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> listarCategorias(){
        return ResponseEntity.ok(estoqueService.listarTodasCategorias());
    }

    @GetMapping("/categorias/{categoriaId}/subcategorias")
    public ResponseEntity<List<SubCategoria>> listarSubCategorias(@PathVariable Long categoriaId){
        System.out.println(">>> [BACKEND] CHEGOU REQUISIÇÃO LISTAR SUBCATEGORIAS DA CATEGORIA: " + categoriaId);
        return ResponseEntity.ok(estoqueService.listarSubCategoriaPorCategoriaId(categoriaId));
    }

    @GetMapping("/subcategorias/{subCategoriaId}/produtos")
    public ResponseEntity<List<Produto>> listarProdutos(@PathVariable Long subCategoriaId){
        return ResponseEntity.ok(estoqueService.listarProdutosPorSubCategoriaId(subCategoriaId));
    }

    @GetMapping("/produtos/{produtoId}")
    public ResponseEntity<Produto> buscarProdutoPorId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(estoqueService.buscarProdutoPorId(produtoId));
    }

    @GetMapping("/produtos/buscar")
    public ResponseEntity<List<Produto>> buscarProdutosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(estoqueService.buscarProdutosPorNome(nome));
    }

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = estoqueService.criarCategoria(categoria);
        return ResponseEntity.created(URI.create("/estoque/categorias/" + novaCategoria.getId())).body(novaCategoria);
    }

    @PostMapping("/categorias/{categoriaId}/subcategorias")
    public ResponseEntity<SubCategoria> criarSubCategoria(@RequestBody SubCategoria subCategoria, @PathVariable Long categoriaId) {
        SubCategoria novaSubCategoria = estoqueService.criarSubCategoria(subCategoria, categoriaId);
        return ResponseEntity.created(URI.create("/estoque/subcategorias/" + novaSubCategoria.getId())).body(novaSubCategoria);
    }

    @PostMapping("/subcategorias/{subCategoriaId}/produtos")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto, @PathVariable Long subCategoriaId) {
        Produto novoProduto = estoqueService.criarProduto(produto, subCategoriaId);
        return ResponseEntity.created(URI.create("/estoque/produtos/" + novoProduto.getId())).body(novoProduto);
    }

    @PutMapping("/categorias/{id}")
    public ResponseEntity<Categoria> atualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaDetails) {
        return ResponseEntity.ok(estoqueService.atualizarCategoria(id, categoriaDetails));
    }

    @PutMapping("/subcategorias/{id}")
    public ResponseEntity<SubCategoria> atualizarSubCategoria(@PathVariable Long id, @RequestBody SubCategoria subCategoriaDetails) {
        return ResponseEntity.ok(estoqueService.atualizarSubCategoria(id, subCategoriaDetails));
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoDetails) {
        return ResponseEntity.ok(estoqueService.atualizarProduto(id, produtoDetails));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        estoqueService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/subcategorias/{id}")
    public ResponseEntity<Void> deletarSubCategoria(@PathVariable Long id) {
        estoqueService.deletarSubCategoria(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        estoqueService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }
}