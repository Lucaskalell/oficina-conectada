package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long> {

    int countByStatusIn(List<StatusOrdemDeServico> statuses);
    int countByStatus(StatusOrdemDeServico status);


    List<OrdemDeServico> findTop4ByOrderByIdDesc();
    List<OrdemDeServico> findByDataEntradaAfter(LocalDateTime data);
}
