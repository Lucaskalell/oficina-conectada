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

    public Categoria criarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public SubCategoria criarSubCategoria(SubCategoria subCategoria, Long categoriaId) {
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + categoriaId));
        subCategoria.setCategoria(categoria);
        return subCategoriaRepository.save(subCategoria);
    }

    public Produto criarProduto(Produto produto, Long subCategoriaId) {
        SubCategoria subCategoria = subCategoriaRepository.findById(subCategoriaId)
                .orElseThrow(() -> new RuntimeException("SubCategoria não encontrada: " + subCategoriaId));
        produto.setSubCategoria(subCategoria);
        return produtoRepository.save(produto);
    }

    public void deletarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    public void deletarSubCategoria(Long id) {
        if (!subCategoriaRepository.existsById(id)) {
            throw new RuntimeException("SubCategoria não encontrada: " + id);
        }
        subCategoriaRepository.deleteById(id);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado: " + id);
        }
        produtoRepository.deleteById(id);
    }
}
