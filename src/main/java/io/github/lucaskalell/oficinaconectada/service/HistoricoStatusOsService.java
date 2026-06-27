package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.HistoricoStatusOs;
import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.entity.Usuario;
import io.github.lucaskalell.oficinaconectada.repository.HistoricoStatusOsRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.UsuarioRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoricoStatusOsService {

    private final HistoricoStatusOsRepository historicoRepository;
    private final OrdemDeServicoRepository ordemDeServicoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<HistoricoStatusOs> listarPorOrdem(Long ordemDeServicoId) {
        return historicoRepository.findByOrdemDeServicoIdOrderByCreatedAtDesc(ordemDeServicoId);
    }

    @Transactional
    public void registrarMudancaStatus(Long ordemId, StatusOrdemDeServico novoStatus, Long usuarioId, String observacao) {
        OrdemDeServico os = ordemDeServicoRepository.findById(ordemId)
                .orElseThrow(() -> new RuntimeException("Ordem de serviço não encontrada: " + ordemId));

        Usuario usuario = null;
        if (usuarioId != null) {
            usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + usuarioId));
        }

        HistoricoStatusOs historico = new HistoricoStatusOs();
        historico.setOrdemDeServico(os);
        historico.setStatusAnterior(os.getStatus() != null ? os.getStatus().name() : null);
        historico.setStatusNovo(novoStatus.name());
        historico.setUsuario(usuario);
        historico.setObservacao(observacao);

        historicoRepository.save(historico);

        os.setStatus(novoStatus);
        ordemDeServicoRepository.save(os);
    }
}
