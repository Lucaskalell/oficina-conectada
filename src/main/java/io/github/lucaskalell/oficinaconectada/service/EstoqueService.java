package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.Categoria;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.SubCategoria;
import io.github.lucaskalell.oficinaconectada.repository.CategoriaRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.SubCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final CategoriaRepository categoriaRepository;
    private final SubCategoriaRepository subCategoriaRepository;
    private final ProdutoRepository produtoRepository;

    public List<Categoria> listarTodasCategorias(){
        return categoriaRepository.findAll();
    }

    public List<SubCategoria> listarSubCategoriaPorCategoriaId(Long categoriaId){
        return subCategoriaRepository.findByCategoriaId(categoriaId);
    }

   public List<Produto> listarProdutosPorSubCategoriaId(Long subCategoriaId){
        return produtoRepository.findBySubCategoriaId(subCategoriaId);

   }

   public Produto buscarProdutoPorId(Long produtoId){
        return produtoRepository.findById(produtoId)
                .orElseThrow(()-> new RuntimeException("Produto não encontrado"+produtoId));
   }

   public List<Produto> buscarProdutosPorNome(String nome){
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
   }
}
