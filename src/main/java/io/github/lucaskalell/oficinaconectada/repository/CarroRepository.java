package io.github.lucaskalell.oficinaconectada.repository;

import io.github.lucaskalell.oficinaconectada.dto.CarroStatusDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarroRepository extends JpaRepository<Carro, Long> {
    Optional<Carro> findByPlaca(String placa);
    List<Carro> findByClienteId(Long clienteId);
    Optional<Carro> findById(Long id);

    @Query("SELECT new io.github.lucaskalell.oficinaconectada.dto.CarroStatusDTO(" +
            "new io.github.lucaskalell.oficinaconectada.dto.CarroDTO(" +
            "c.id," +
            " c.placa," +
            " c.modelo," +
            " c.ano," +
            " c.cor," +
            " c.cliente.id," +
            " c.cliente.nome), " +
            "os.status) " +
            "FROM Carro c " +
            "LEFT JOIN OrdemDeServico os ON os.carro = c " +
            "WHERE os.id = (SELECT MAX(os2.id) FROM OrdemDeServico os2 WHERE os2.carro = c) " +
            "OR os.id IS NULL")
    List<CarroStatusDTO> carrosComStatus();
}