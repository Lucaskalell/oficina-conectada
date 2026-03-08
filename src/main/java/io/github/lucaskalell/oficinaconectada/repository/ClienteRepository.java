package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@NonNullApi
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCpf(String cpf);
    Optional<Cliente> findByNomeContainingIgnoreCase(String nome);

    @EntityGraph(attributePaths = "carros")
    Optional<Cliente> findWithCarrosById(Long id);

    //para fins de estudo começarei adicona JPQL mesma forma que utilizo na empresa
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.carros WHERE c.id = :id")
    Optional<Cliente> findWithCarrosByIdQuery(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.carros")
    List<Cliente> findAllWithCarros();
}
