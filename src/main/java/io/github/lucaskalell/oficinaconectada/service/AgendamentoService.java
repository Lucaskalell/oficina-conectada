package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.entity.Agendamento;
import io.github.lucaskalell.oficinaconectada.entity.Carro;
import io.github.lucaskalell.oficinaconectada.entity.Cliente;
import io.github.lucaskalell.oficinaconectada.entity.Mecanico;
import io.github.lucaskalell.oficinaconectada.repository.AgendamentoRepository;
import io.github.lucaskalell.oficinaconectada.repository.CarroRepository;
import io.github.lucaskalell.oficinaconectada.repository.ClienteRepository;
import io.github.lucaskalell.oficinaconectada.repository.MecanicoRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusAgendamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final ClienteRepository clienteRepository;
    private final CarroRepository carroRepository;
    private final MecanicoRepository mecanicoRepository;

    public List<Agendamento> listarTodos() {
        return agendamentoRepository.findAll();
    }

    public List<Agendamento> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return agendamentoRepository.findByDataHoraBetweenOrderByDataHoraAsc(inicio, fim);
    }

    public Agendamento buscarPorId(Long id) {
        return agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado: " + id));
    }

    @Transactional
    public Agendamento criar(Long clienteId, Long carroId, Long mecanicoId, LocalDateTime dataHora, String descricaoServico) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado: " + clienteId));

        Carro carro = carroRepository.findById(carroId)
                .orElseThrow(() -> new RuntimeException("Carro não encontrado: " + carroId));

        if (!carro.getCliente().getId().equals(clienteId)) {
            throw new RuntimeException("Este carro não pertence ao cliente informado");
        }

        Mecanico mecanico = null;
        if (mecanicoId != null) {
            mecanico = mecanicoRepository.findById(mecanicoId)
                    .orElseThrow(() -> new RuntimeException("Mecânico não encontrado: " + mecanicoId));
        }

        Agendamento agendamento = new Agendamento();
        agendamento.setCliente(cliente);
        agendamento.setCarro(carro);
        agendamento.setMecanico(mecanico);
        agendamento.setDataHora(dataHora);
        agendamento.setDescricaoServico(descricaoServico);
        agendamento.setStatus(StatusAgendamento.AGENDADO);

        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public Agendamento atualizarStatus(Long id, StatusAgendamento novoStatus) {
        Agendamento agendamento = buscarPorId(id);
        agendamento.setStatus(novoStatus);
        return agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void deletar(Long id) {
        if (!agendamentoRepository.existsById(id)) {
            throw new RuntimeException("Agendamento não encontrado: " + id);
        }
        agendamentoRepository.deleteById(id);
    }
}
