package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FaturamentoMensalDTO {
    private String mes;
    private double receita;
    private double despesa;
}
