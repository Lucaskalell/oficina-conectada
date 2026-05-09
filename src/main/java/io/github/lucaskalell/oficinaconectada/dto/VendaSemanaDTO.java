package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaSemanaDTO {
    private String dias;
    private Double valor;
}