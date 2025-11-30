package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CategoriaResumoDTO;
import io.github.lucaskalell.oficinaconectada.dto.EstoqueResumoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Categoria;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import io.github.lucaskalell.oficinaconectada.entity.SubCategoria;
import io.github.lucaskalell.oficinaconectada.repository.CategoriaRepository;
import io.github.lucaskalell.oficinaconectada.repository.ItemVendaRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.SubCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EstoqueService {
    private final CategoriaRepository categoriaRepository;
    private final SubCategoriaRepository subCategoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;

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

    public Categoria atualizarCategoria(Long id, Categoria categoriaDetails) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + id));
        categoria.setNome(categoriaDetails.getNome());
        return categoriaRepository.save(categoria);
    }

    public SubCategoria atualizarSubCategoria(Long id, SubCategoria subCategoriaDetails) {
        SubCategoria subCategoria = subCategoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SubCategoria não encontrada: " + id));
        subCategoria.setNome(subCategoriaDetails.getNome());
        return subCategoriaRepository.save(subCategoria);
    }

    public Produto atualizarProduto(Long id, Produto produtoDetails) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + id));

        produto.setNome(produtoDetails.getNome());
        produto.setDescricao(produtoDetails.getDescricao());
        produto.setQuantidadeEmEstoque(produtoDetails.getQuantidadeEmEstoque());
        produto.setPrecoCusto(produtoDetails.getPrecoCusto());
        produto.setPrecoVenda(produtoDetails.getPrecoVenda());

        return produtoRepository.save(produto);
    }

    public EstoqueResumoDTO getEstoqueResumo() {
        long totalPecas = produtoRepository.count();
        long baixoEstoque = produtoRepository.countByQuantidadeEmEstoqueLessThan(10);

        List<String> nomesProdutosMaisVendidos = itemVendaRepository.findNomeProdutoMaisVendido();
        String itemMaisVendido = nomesProdutosMaisVendidos.isEmpty() ? "Nenhuma venda registrada" : nomesProdutosMaisVendidos.get(0);

        List<CategoriaResumoDTO> categoriasResumo = categoriaRepository.findAll().stream()
                .map(categoria -> new CategoriaResumoDTO(
                        categoria.getId(),
                        categoria.getNome(),
                        (long) categoria.getSubCategorias().stream()
                                .mapToLong(sub -> sub.getProdutos().size())
                                .sum()
                ))
                .collect(Collectors.toList());

        return new EstoqueResumoDTO(
                totalPecas,
                itemMaisVendido,
                baixoEstoque,
                categoriasResumo
        );
    }
}
