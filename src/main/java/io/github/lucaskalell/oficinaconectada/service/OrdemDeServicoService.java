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
import io.github.lucaskalell.oficinaconectada.exception.ArmazenamentoArquivoException;
import io.github.lucaskalell.oficinaconectada.exception.CarroNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.exception.ClienteNaoEncontradoException;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import jakarta.transaction.Transactional;
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
public class OrdemDeServicoService {
    private final String DIRETORIO_FOTOS = "uploads/fotos_os/";
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

    public List<OrdemDeServicoDTO> listarResumo() {
        List<OrdemDeServico> entidades = ordemDeServicoRepository.findAll();
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
                            fotosDTO,
                            os.getMecanicoResponsavel(),
                            os.getPrioridade()
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
            throw new RuntimeException("Este carro não pertence ao cliente informado");
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

        OrdemDeServico ordemSalva = ordemDeServicoRepository.save(ordemDeServico);

        return converterParaDto(ordemSalva);
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

            String nomeArquivo = UUID.randomUUID().toString() + "_" + arquivoFoto.getOriginalFilename();
            Path caminhoArquivo = caminhoDiretorio.resolve(nomeArquivo);
            Files.copy(arquivoFoto.getInputStream(), caminhoArquivo, StandardCopyOption.REPLACE_EXISTING);

            FotoOrdemDeServico fotoEntidade = new FotoOrdemDeServico();
            fotoEntidade.setCaminhoFoto(caminhoArquivo.toString());
            fotoEntidade.setLegenda(arquivoFoto.getOriginalFilename());
            fotoEntidade.setOrdemDeServico(os);

            if (os.getFotos() == null) {
                os.setFotos(new ArrayList<>());
            }
            os.getFotos().add(fotoEntidade);
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
                        item.getValorTotal()
                )).collect(Collectors.toList()) : Collections.emptyList();

        List<FotoOrdemDeServicoDTO> fotosDTO = os.getFotos() != null ? os.getFotos().stream()
                .map(foto -> new FotoOrdemDeServicoDTO(
                        foto.getCaminhoFoto(),
                        foto.getLegenda()
                )).collect(Collectors.toList()) : Collections.emptyList();

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
                os.getValorSubtotalPecas(),
                itensDTO,
                fotosDTO,
                os.getMecanicoResponsavel(),
                os.getPrioridade()
        );
    }
}