package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.dto.EstoqueBaixoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findBySubCategoriaId (Long subCategoriaId);

    long countByQuantidadeEmEstoqueLessThan(int quantidade);



    // Adicionei esta query para buscar apenas as peças acabando
    @Query("SELECT new io.github.lucaskalell.oficinaconectada.dto.EstoqueBaixoDTO(" +
            "p.nome, p.quantidadeEmEstoque, p.quantidadeMinima) " +
            "FROM Produto p " +
            "WHERE p.quantidadeEmEstoque <= p.quantidadeMinima")
    List<EstoqueBaixoDTO> buscarEstoqueBaixo();
}
