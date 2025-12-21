package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findById(Long id);
}
