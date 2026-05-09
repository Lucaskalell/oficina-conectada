package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.dto.EstoqueBaixoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findBySubCategoriaId (Long subCategoriaId);

    long countByQuantidadeEmEstoqueLessThan(int quantidade);

    @Query("SELECT new io.github.lucaskalell.oficinaconectada.dto.EstoqueBaixoDTO(" +
            "p.nome, p.quantidadeEmEstoque, p.quantidadeMinima) " +
            "FROM Produto p " +
            "WHERE p.quantidadeEmEstoque <= p.quantidadeMinima")
    List<EstoqueBaixoDTO> buscarEstoqueBaixo();
}