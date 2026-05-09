package io.github.lucaskalell.oficinaconectada.service;

import io.github.lucaskalell.oficinaconectada.dto.*;
import io.github.lucaskalell.oficinaconectada.entity.Agendamento;
import io.github.lucaskalell.oficinaconectada.entity.OrdemDeServico;
import io.github.lucaskalell.oficinaconectada.repository.AgendamentoRepository;
import io.github.lucaskalell.oficinaconectada.repository.OrdemDeServicoRepository;
import io.github.lucaskalell.oficinaconectada.repository.ProdutoRepository;
import io.github.lucaskalell.oficinaconectada.repository.VendaRepository;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OrdemDeServicoRepository ordemDeServicoRepository;
    private final ProdutoRepository produtoRepository;
    private final VendaRepository vendaRepository;
    private final AgendamentoRepository agendamentoRepository;

    public DashboardDTO obterResumo() {
        DashboardDTO dto = new DashboardDTO();
        List<StatusOrdemDeServico> statusAbertos = Arrays.asList(
                StatusOrdemDeServico.EM_ANDAMENTO,
                StatusOrdemDeServico.NAO_INICIADO,
                StatusOrdemDeServico.AGUARDANDO_PECA,
                StatusOrdemDeServico.AGUARDANDO_RETIRADA
        );
        dto.setOsAbertas(ordemDeServicoRepository.countByStatusIn(statusAbertos));
        dto.setVeiculosEmServico(ordemDeServicoRepository.countByStatus(StatusOrdemDeServico.EM_ANDAMENTO));
        dto.setEstoqueBaixo(produtoRepository.buscarEstoqueBaixo());
        List<OrdemDeServico> ultimasOs = ordemDeServicoRepository.findTop4ByOrderByIdDesc();
        List<OrdemRecenteDTO> recentesDto = ultimasOs.stream().map(os -> {
            String carro = os.getCarro() != null ? os.getCarro().getModelo() : "Veículo Indefinido";
            String cliente = os.getCliente() != null ? os.getCliente().getNome() : "Cliente Indefinido";
            return new OrdemRecenteDTO(
                    "OS-" + os.getId(),
                    os.getDescricaoServico() != null ? os.getDescricaoServico() : "Serviço Geral",
                    cliente + " - " + carro,
                    "Recente",
                    os.getStatus().name()
            );
        }).collect(Collectors.toList());
        dto.setOrdensRecentes(recentesDto);
        LocalDateTime seisMesesAtras = LocalDateTime.now().minusMonths(5)
                .with(java.time.temporal.TemporalAdjusters.firstDayOfMonth())
                .with(java.time.LocalTime.MIN);
        List<OrdemDeServico> osUltimos6Meses = ordemDeServicoRepository.findByDataEntradaAfter(seisMesesAtras);

        List<FaturamentoMensalDTO> graficoFaturamento = new ArrayList<>();
        List<ServicosMensalDTO> graficoServicos = new ArrayList<>();
        double faturamentoAtual = 0.0;
        int concluidasAtual = 0;
        java.time.YearMonth mesAtual = java.time.YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            java.time.YearMonth ym = mesAtual.minusMonths(i);
            List<OrdemDeServico> concluidasDoMes = osUltimos6Meses.stream()
                    .filter(os -> os.getDataEntrada() != null && java.time.YearMonth.from(os.getDataEntrada()).equals(ym))
                    .filter(os -> os.getStatus() == StatusOrdemDeServico.FINALIZADO)
                    .toList();
            int totalOsConcluidas = concluidasDoMes.size();
            double receitaMes = concluidasDoMes.stream()
                    .mapToDouble(os -> os.getValorTotal() != null ? os.getValorTotal().doubleValue() : 0.0)
                    .sum();
            String nomeMes = ym.getMonth().getDisplayName(java.time.format.TextStyle.SHORT, new Locale("pt", "BR"));
            nomeMes = nomeMes.substring(0, 1).toUpperCase() + nomeMes.substring(1).replace(".", "");
            graficoFaturamento.add(new FaturamentoMensalDTO(nomeMes, receitaMes, 0.0));
            graficoServicos.add(new ServicosMensalDTO(nomeMes, totalOsConcluidas));
            if (i == 0) {
                faturamentoAtual = receitaMes;
                concluidasAtual = totalOsConcluidas;
            }
        }

        LocalDateTime inicioDoDia = LocalDateTime.now().with(java.time.LocalTime.MIN);
        LocalDateTime fimDoDia = LocalDateTime.now().with(java.time.LocalTime.MAX);

        List<Agendamento> agendamentosDeHoje = agendamentoRepository.findByDataHoraBetweenOrderByDataHoraAsc(inicioDoDia, fimDoDia);

        List<AgendamentoDTO> agendaDto = agendamentosDeHoje.stream().map(agenda -> {
            String horario = String.format("%02d:%02d", agenda.getDataHora().getHour(), agenda.getDataHora().getMinute());
            String carro = agenda.getCarro() != null ? agenda.getCarro().getModelo() : "Veículo";
            String cliente = agenda.getCliente() != null ? agenda.getCliente().getNome() : "Cliente";

            return new AgendamentoDTO(
                    horario,
                    agenda.getDescricaoServico(),
                    cliente + " - " + carro,
                    agenda.getMecanicoResponsavel() != null ? agenda.getMecanicoResponsavel() : "A definir",
                    agenda.getStatus().getDescricao()
            );
        }).collect(Collectors.toList());

        dto.setAgendaHoje(agendaDto);
        dto.setFaturamentoMensal(faturamentoAtual);
        dto.setOsConcluidas(concluidasAtual);
        dto.setGraficoFaturamento(graficoFaturamento);
        dto.setGraficoServicos(graficoServicos);

        return dto;
    }
}