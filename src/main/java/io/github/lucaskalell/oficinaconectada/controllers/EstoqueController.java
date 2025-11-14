package io.github.lucaskalell.oficinaconectada.controllers;


import io.github.lucaskalell.oficinaconectada.entity.Categoria;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.SubCategoria;
import io.github.lucaskalell.oficinaconectada.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estoque")
@RequiredArgsConstructor
public class EstoqueController {
    private final EstoqueService estoqueService;


    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> getTodasCategorias(){
        List<Categoria>categorias = estoqueService.listarTodasCategorias();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/categorias/{categoriaId}/subCategorias")
    public ResponseEntity<List<SubCategoria>> getSubCategoriasPorCategoriaId(
            @PathVariable Long categoriaId
    ){
        List<SubCategoria>subCategorias = estoqueService.listarSubCategoriaPorCategoriaId(categoriaId);
        return ResponseEntity.ok(subCategorias);
    }

    @GetMapping("/subCategorias/{subCategoriaId}/produtos")
    public ResponseEntity<List<Produto>>getProdutosPorSubCategoria(
            @PathVariable Long subCategoriaId

    ){
        List<Produto>produtos = estoqueService.listarProdutosPorSubCategoriaId(subCategoriaId);
        return ResponseEntity.ok(produtos);
    }
    @GetMapping("/produtos/{produtoId}")
    public ResponseEntity<Produto> getProdutoPorId(
            @PathVariable Long produtoId
    ) {
        Produto produto = estoqueService.buscarProdutoPorId(produtoId);
        return ResponseEntity.ok(produto);
    }
    @GetMapping("/produtos/buscar")
    public ResponseEntity<List<Produto>> buscarProdutosPorNome(
            @RequestParam String nome
    ) {
        List<Produto> produtos = estoqueService.buscarProdutosPorNome(nome);
        return ResponseEntity.ok(produtos);
    }
}
