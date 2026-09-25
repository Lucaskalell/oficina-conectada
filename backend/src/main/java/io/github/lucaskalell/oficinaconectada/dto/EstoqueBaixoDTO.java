package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueBaixoDTO {
    private String nomePeca;
    private int quantidadeAtual;
    private int quantidadeMinima;
}