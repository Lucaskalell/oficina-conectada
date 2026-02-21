package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.dto.FotoOrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.dto.ItemServicoDTO;
import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.entity.FotoOrdemDeServico;
import io.github.lucaskalell.oficinaconectada.entity.ItemServico;
import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.exception.CarroNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.exception.ClienteNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
            List<ItemServicoDTO> itensDTO = os.getItens() != null ? os.getItens().stream()
                    .map(item -> new ItemServicoDTO(
                            item.getDescricao(),
                            item.getQuantidade(),
                            item.getValorUnitario(),
                            item.getValorTotal()
                    )).collect(Collectors.toList()) : Collections.emptyList();

            List<FotoOrdemDeServicoDTO> fotosDTO = os.getFotos() != null ? os.getFotos().stream()
                    .map(foto -> new FotoOrdemDeServicoDTO(
                            foto.getCaminhoFoto(),
                            foto.getLegenda()
                    )).collect(Collectors.toList()) : Collections.emptyList();

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
                            os.getValorTotal(),
                            os.getValorSubtotalPecas(),
                            itensDTO,
                            fotosDTO
                    )
            );
        }
        return osDTO;
    }

    @Transactional
    public OrdemDeServicoDTO criar(CriarOrdemDeServicoRequest request) {
        Cliente cliente = clienteRepository
                .findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));
        Carro carro = carroRepository
                .findById(request.getCarroId())
                .orElseThrow(() -> new CarroNaoEncontradoException("Carro não encontrado"));
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
        ordemDeServico.setValorSubtotalPecas(request.getValorSubtotalPecas());

        if (request.getItens() != null) {
            List<ItemServico> itens = request.getItens().stream().map(itemDTO -> {
                ItemServico item = new ItemServico();
                item.setDescricao(itemDTO.getDescricao());
                item.setQuantidade(itemDTO.getQuantidade());
                item.setValorUnitario(itemDTO.getValorUnitario());
                item.setValorTotal(itemDTO.getValorTotal());
                item.setOrdemDeServico(ordemDeServico);
                return item;
            }).collect(Collectors.toList());
            ordemDeServico.setItens(itens);
        }

        if (request.getFotos() != null) {
            List<FotoOrdemDeServico> fotos = request.getFotos().stream().map(fotoDTO -> {
                FotoOrdemDeServico foto = new FotoOrdemDeServico();
                foto.setCaminhoFoto(fotoDTO.getCaminhoFoto());
                foto.setLegenda(fotoDTO.getLegenda());
                foto.setOrdemDeServico(ordemDeServico);
                return foto;
            }).collect(Collectors.toList());
            ordemDeServico.setFotos(fotos);
        }

        OrdemDeServico ordemSalva = ordemDeServicoRepository.save(ordemDeServico);

        List<ItemServicoDTO> itensDTO = ordemSalva.getItens() != null ? ordemSalva.getItens().stream()
                .map(item -> new ItemServicoDTO(
                        item.getDescricao(),
                        item.getQuantidade(),
                        item.getValorUnitario(),
                        item.getValorTotal()
                )).collect(Collectors.toList()) : Collections.emptyList();

        List<FotoOrdemDeServicoDTO> fotosDTO = ordemSalva.getFotos() != null ? ordemSalva.getFotos().stream()
                .map(foto -> new FotoOrdemDeServicoDTO(
                        foto.getCaminhoFoto(),
                        foto.getLegenda()
                )).collect(Collectors.toList()) : Collections.emptyList();

        return new OrdemDeServicoDTO(
                ordemSalva.getId(),
                ordemSalva.getStatus(),
                ordemSalva.getCarro().getPlaca(),
                ordemSalva.getCliente().getNome(),
                ordemSalva.getCarro().getModelo(),
                ordemSalva.getDataEntrada(),
                ordemSalva.getDefeito(),
                ordemSalva.getDescricaoServico(),
                ordemSalva.getValorTotal(),
                ordemSalva.getValorSubtotalPecas(),
                itensDTO,
                fotosDTO
        );

    }

}
