package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.entity.HistoricoStatusOs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoricoStatusOsRepository extends JpaRepository<HistoricoStatusOs, Long> {
    List<HistoricoStatusOs> findByOrdemDeServicoIdOrderByCreatedAtDesc(Long ordemDeServicoId);
}
