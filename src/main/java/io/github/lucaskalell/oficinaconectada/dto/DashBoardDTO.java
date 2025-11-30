package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashBoardDTO {
    private double faturamentoMensal;
    private Integer totalOrdensAbertas;
    private Integer produtosBaixosEstoque;
    private List<VendaSemanaDTO> vendasSemana;
    private Map<String, Integer> statusOrdens;

}
