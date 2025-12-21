package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.OrdemDeServicoDTO;
import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;


@Service
public class OrdemDeServicoService {
    private final OrdemDeServicoRepository ordemDeServicoRepository;

    public OrdemDeServicoService(
            OrdemDeServicoRepository ordemDeServicoRepository
    ) {
        this.ordemDeServicoRepository = ordemDeServicoRepository;
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
}
