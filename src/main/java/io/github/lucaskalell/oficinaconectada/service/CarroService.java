package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CarroDTO;
import io.github.lucaskalell.oficinaconectada.dto.CarroStatusDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarroService {

    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public List<CarroDTO> listarTodos() {
        return carroRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    public List<CarroStatusDTO> listarComStatusDeServico() {
        return carroRepository.carrosComStatus();
    }

    @Transactional
    public CarroDTO criar(CarroDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Carro carro = new Carro();
        carro.setPlaca(dto.getPlaca());
        carro.setModelo(dto.getModelo());
        carro.setAno(dto.getAno());
        carro.setCor(dto.getCor());
        carro.setCliente(cliente);

        carro = carroRepository.save(carro);
        return converterParaDto(carro);
    }

    @Transactional
    public CarroDTO atualizar(Long id, CarroDTO dto) {
        Carro carro = carroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));

        carro.setPlaca(dto.getPlaca());
        carro.setModelo(dto.getModelo());
        carro.setAno(dto.getAno());
        carro.setCor(dto.getCor());
        
        if (dto.getClienteId() != null) {
             Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
             carro.setCliente(cliente);
        }

        carro = carroRepository.save(carro);
        return converterParaDto(carro);
    }

    @Transactional
    public void deletar(Long id) {
        carroRepository.deleteById(id);
    }

    private CarroDTO converterParaDto(Carro carro) {
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
