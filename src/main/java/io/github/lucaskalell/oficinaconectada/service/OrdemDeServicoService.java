package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrdemDeServicoService {
    private final OrdemDeServicoRepository ordemDeServicoRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;

    public OrdemDeServicoService(
            OrdemDeServicoRepository ordemDeServicoRepository,
            CarroRepository carroRepository,
            ClienteRepository clienteRepository
    ) {
        this.ordemDeServicoRepository = ordemDeServicoRepository;
        this.carroRepository = carroRepository;
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public List<OrdemDeServicoDTO> listarResumo() {
        List<OrdemDeServico> entidades =
                ordemDeServicoRepository.findAll();
        List<OrdemDeServicoDTO> osDTO = new ArrayList<>();
        for (OrdemDeServico os : entidades) {
            osDTO.add(
                    new OrdemDeServicoDTO(
                            os.getId(),
                            os.getStatus(),
                            os.getCarro().getPlaca(),
                            os.getCliente().getNome(),
                            os.getCarro().getModelo(),
                            os.getDataEntrada(),
                            os.getDefeito(),
                            os.getDescricaoServico(),
                            os.getValorTotal()
                    )
            );
        }
        return osDTO;
    }

    public OrdemDeServicoDTO criar(CriarOrdemDeServicoRequest request) {
        Cliente cliente = clienteRepository
                .findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        Carro carro = carroRepository
                .findById(request.getCarroId())
                .orElseThrow(() -> new RuntimeException("Carro não encontrado"));
        if (!carro.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Este carro nao pertence ao cliente informado");
        }
        OrdemDeServico ordemDeServico = new OrdemDeServico();
        ordemDeServico.setCliente(cliente);
        ordemDeServico.setCarro(carro);
        ordemDeServico.setStatus(StatusOrdemDeServico.NAO_INICIADO);
        ordemDeServico.setDataEntrada(LocalDateTime.now());
        ordemDeServico.setDefeito(request.getDefeito());
        ordemDeServico.setDescricaoServico(request.getDescricaoServico());
        ordemDeServico.setValorTotal(request.getValorTotal());
        OrdemDeServico ordemSalva = ordemDeServicoRepository.save(ordemDeServico);
        return new OrdemDeServicoDTO(
                ordemSalva.getId(),
                ordemSalva.getStatus(),
                ordemSalva.getCarro().getPlaca(),
                ordemSalva.getCliente().getNome(),
                ordemSalva.getCarro().getModelo(),
                ordemSalva.getDataEntrada(),
                ordemSalva.getDefeito(),
                ordemSalva.getDescricaoServico(),
                ordemSalva.getValorTotal()
        );

    }
}
