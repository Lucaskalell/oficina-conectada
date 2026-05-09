package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CarroDTO;
import io.github.lucaskalell.oficinaconectada.dto.ClienteCarroRequestDTO;
import io.github.lucaskalell.oficinaconectada.dto.ClienteDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.exception.ClienteNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.mapper.ClienteMapper;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CarroRepository carroRepository;
    private final ClienteMapper clienteMapper;

    @Transactional
    public ClienteDTO criarClienteComCarro(ClienteCarroRequestDTO request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setCpf(request.getCpf());
        cliente.setTelefone(request.getTelefone());
        cliente.setEmail(request.getEmail());
        Cliente clienteSalvo = clienteRepository.save(cliente);

        if (request.getPlaca() != null && !request.getPlaca().isEmpty()) {
            Carro carro = new Carro();
            carro.setPlaca(request.getPlaca());
            carro.setModelo(request.getModelo());
            carro.setMarca(request.getMarca());
            carro.setCor(request.getCor());
            carro.setAno(request.getAno());
            carro.setCliente(clienteSalvo);

            carroRepository.save(carro);
        }
        return new ClienteDTO(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getCpf(),
                clienteSalvo.getTelefone(),
                clienteSalvo.getEmail()
        );
    }

    @Transactional
    public ClienteDTO criarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEmail(clienteDTO.getEmail());
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteDTO(
                clienteSalvo.getId(),
                clienteSalvo.getNome(),
                clienteSalvo.getCpf(),
                clienteSalvo.getTelefone(),
                clienteSalvo.getEmail()
        );
    }

    public List<ClienteDTO> listarTodosClientes() {
        return clienteRepository.findAll().stream()
                .map(cliente -> new ClienteDTO(
                        cliente.getId(),
                        cliente.getNome(),
                        cliente.getCpf(),
                        cliente.getTelefone(),
                        cliente.getEmail()
                ))
                .collect(Collectors.toList());
    }

    public List<ClienteDTO> listarTodosClientesComCarro() {
        return clienteRepository.findAllWithCarros().stream()
                .map(cliente -> {
                    ClienteDTO dto = new ClienteDTO(
                            cliente.getId(),
                            cliente.getNome(),
                            cliente.getCpf(),
                            cliente.getTelefone(),
                            cliente.getEmail()
                    );
                    if (cliente.getCarros() != null && !cliente.getCarros().isEmpty()) {
                        List<CarroDTO> carrosDto = cliente.getCarros().stream()
                                .map(carro -> {
                                    CarroDTO cDto = new CarroDTO();
                                    cDto.setId(carro.getId());
                                    cDto.setPlaca(carro.getPlaca());
                                    cDto.setModelo(carro.getModelo());
                                    cDto.setAno(carro.getAno());
                                    return cDto;
                                })
                                .collect(Collectors.toList());

                        dto.setCarros(carrosDto);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException
                        ("Cliente não encontrado com o ID: " + id));
        return new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail()
        );
    }

    public ClienteDTO buscarClienteECarroCompletoPorId(Long id) {
        Cliente cliente = clienteRepository.findWithCarrosByIdQuery(id)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + id));
        return clienteMapper.toDto(cliente);
    }

    @Transactional
    public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteAtualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizado.getNome());
                    cliente.setCpf(clienteAtualizado.getCpf());
                    cliente.setEmail(clienteAtualizado.getEmail());
                    cliente.setTelefone(clienteAtualizado.getTelefone());
                    Cliente clienteSalvo = clienteRepository.save(cliente);
                    return new ClienteDTO(
                            clienteSalvo.getId(),
                            clienteSalvo.getNome(),
                            clienteSalvo.getCpf(),
                            clienteSalvo.getTelefone(),
                            clienteSalvo.getEmail()
                    );
                })
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + id));
    }

    @Transactional
    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}