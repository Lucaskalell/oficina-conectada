package io.github.lucaskalell.oficinaconectada.mapper;

import io.github.lucaskalell.oficinaconectada.dto.CarroDTO;
import io.github.lucaskalell.oficinaconectada.dto.ClienteDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClienteMapper {
    public ClienteDTO toDto(Cliente cliente) {
        List<CarroDTO> carrosDTO;
        carrosDTO = cliente.getCarros().stream()
                .map(this::toCarroDto)
                .toList();
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail(),
                carrosDTO
        );
    }

    private CarroDTO toCarroDto(Carro carro) {
        return new CarroDTO(
                carro.getId(),
                carro.getPlaca(),
                carro.getModelo(),
                carro.getAno(),
                carro.getCor(),
                carro.getCliente().getId(),
                carro.getCliente().getNome()
        );
    }
}