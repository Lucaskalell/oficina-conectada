package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.entity.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendaRepository extends JpaRepository<ItemVenda, Long> {
    @Query("SELECT iv.produto.nome FROM ItemVenda iv GROUP BY iv.produto.id, iv.produto.nome ORDER BY SUM(iv.quantidade) DESC")
    List<String> findNomeProdutoMaisVendido();
}
