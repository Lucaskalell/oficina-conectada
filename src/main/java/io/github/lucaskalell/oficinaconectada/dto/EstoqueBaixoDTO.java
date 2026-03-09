package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EstoqueBaixoDTO {
    private String nomePeca;
    private int quantidadeAtual;
    private int quantidadeMinima;
}
