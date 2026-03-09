package io.github.lucaskalell.oficinaconectada.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DashBoardDTO {
    private double faturamentoMensal;
    private int osAbertas;
    private int osConcluidas;
    private int veiculosEmServico;

    private List<FaturamentoMensalDTO> graficoFaturamento;
    private List<ServicosMensalDTO> graficoServicos;

    private List<OrdemRecenteDTO> ordensRecentes;
    private List<EstoqueBaixoDTO> estoqueBaixo;

    private List<AgendamentoDTO> agendaHoje;


    public DashBoardDTO() {}
}