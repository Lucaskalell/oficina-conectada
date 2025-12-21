package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    Optional<Carro> findByPlaca(String placa);
    List<Carro> findByClienteId(Long clienteId);
    Optional<Carro> findById(Long id);

}