package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.CriarOrdemDeServicoRequest;
import io.github.lucaskalell.oficinaconectada.dto.FotoOrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.dto.ItemServicoDTO;
import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.entity.*;
import io.github.lucaskalell.oficinaconectada.exception.ArmazenamentoArquivoException;
import io.github.lucaskalell.oficinaconectada.exception.CarroNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.exception.ClienteNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.MecanicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdemDeServicoService {

    private static final String DIRETORIO_FOTOS = "uploads/fotos_os/";

    private final OrdemDeServicoRepository ordemDeServicoRepository;
    private final CarroRepository carroRepository;
    private final ClienteRepository clienteRepository;
    private final MecanicoRepository mecanicoRepository;
    private final ProdutoRepository produtoRepository;

    public List<OrdemDeServicoDTO> listarResumo() {
        return ordemDeServicoRepository.findAll().stream()
                .map(this::converterParaDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrdemDeServicoDTO criar(CriarOrdemDeServicoRequest request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        Carro carro = carroRepository.findById(request.getCarroId())
                .orElseThrow(() -> new CarroNaoEncontradoException("Carro não encontrado"));

        if (!carro.getCliente().getId().equals(cliente.getId())) {
            throw new RuntimeException("Este carro não pertence ao cliente informado");
        }

        Mecanico mecanico = null;
        if (request.getMecanicoId() != null) {
            mecanico = mecanicoRepository.findById(request.getMecanicoId())
                    .orElseThrow(() -> new RuntimeException("Mecânico não encontrado"));
        }

        OrdemDeServico os = new OrdemDeServico();
        os.setCliente(cliente);
        os.setCarro(carro);
        os.setMecanico(mecanico);
        os.setStatus(StatusOrdemDeServico.NAO_INICIADO);
        os.setDataEntrada(LocalDateTime.now());
        os.setDefeito(request.getDefeito());
        os.setDescricaoServico(request.getDescricaoServico());
        os.setValorTotal(request.getValorTotal());

        if (request.getItens() != null) {
            List<ItemServico> itens = request.getItens().stream().map(itemDTO -> {
                ItemServico item = new ItemServico();
                item.setDescricao(itemDTO.getDescricao());
                item.setQuantidade(itemDTO.getQuantidade());
                item.setValorUnitario(itemDTO.getValorUnitario());
                item.setOrdemDeServico(os);

                if (itemDTO.getProdutoId() != null) {
                    Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                            .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDTO.getProdutoId()));
                    item.setProduto(produto);
                }

                return item;
            }).collect(Collectors.toList());
            os.setItens(itens);
        }

        return converterParaDto(ordemDeServicoRepository.save(os));
    }

    @Transactional
    public void salvarFoto(Long ordemId, MultipartFile arquivoFoto) {
        OrdemDeServico os = ordemDeServicoRepository.findById(ordemId)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada"));

        try {
            Path caminhoDiretorio = Paths.get(DIRETORIO_FOTOS);
            if (!Files.exists(caminhoDiretorio)) {
                Files.createDirectories(caminhoDiretorio);
            }

            String nomeArquivo = UUID.randomUUID() + "_" + arquivoFoto.getOriginalFilename();
            Path caminhoArquivo = caminhoDiretorio.resolve(nomeArquivo);
            Files.copy(arquivoFoto.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

            FotoOrdemDeServico foto = new FotoOrdemDeServico();
            foto.setCaminhoFoto(caminhoArquivo.toString());
            foto.setLegenda(arquivoFoto.getOriginalFilename());
            foto.setOrdemDeServico(os);

            if (os.getFotos() == null) {
                os.setFotos(new ArrayList<>());
            }
            os.getFotos().add(foto);
            ordemDeServicoRepository.save(os);

        } catch (IOException e) {
            throw new ArmazenamentoArquivoException("Não foi possível salvar a foto", e);
        }
    }

    private OrdemDeServicoDTO converterParaDto(OrdemDeServico os) {
        List<ItemServicoDTO> itensDTO = os.getItens() != null ? os.getItens().stream()
                .map(item -> new ItemServicoDTO(
                        item.getDescricao(),
                        item.getQuantidade(),
                        item.getValorUnitario(),
                        item.getProduto() != null ? item.getProduto().getId() : null
                )).collect(Collectors.toList()) : Collections.emptyList();

        List<FotoOrdemDeServicoDTO> fotosDTO = os.getFotos() != null ? os.getFotos().stream()
                .map(foto -> new FotoOrdemDeServicoDTO(foto.getCaminhoFoto(), foto.getLegenda()))
                .collect(Collectors.toList()) : Collections.emptyList();

        return new OrdemDeServicoDTO(
                os.getId(),
                os.getStatus(),
                os.getCarro().getPlaca(),
                os.getCliente().getNome(),
                os.getCarro().getModelo(),
                os.getDataEntrada(),
                os.getDefeito(),
                os.getDescricaoServico(),
                os.getValorTotal(),
                itensDTO,
                fotosDTO,
                os.getMecanico() != null ? os.getMecanico().getNome() : null,
                os.getPrioridade()
        );
    }
}
