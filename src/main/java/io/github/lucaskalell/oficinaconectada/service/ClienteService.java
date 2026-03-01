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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final CarroRepository carroRepository;
    private final ClienteMapper ClienteMapper;


    @Transactional
    public ClienteDTO criarClienteComCarro(ClienteCarroRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setTelefone(dto.getTelefone());
        cliente.setEmail(dto.getEmail());
        Cliente clienteSalvo = clienteRepository.save(cliente);

        if (dto.getPlaca() != null && !dto.getPlaca().isEmpty()) {
            Carro carro = new Carro();
            carro.setPlaca(dto.getPlaca());
            carro.setModelo(dto.getModelo());
            carro.setMarca(dto.getMarca());
            carro.setCor(dto.getCor());
            carro.setAno(dto.getAno());
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
        return ClienteMapper.toDto(cliente);
    }

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

    public void deletarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado com o ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
}