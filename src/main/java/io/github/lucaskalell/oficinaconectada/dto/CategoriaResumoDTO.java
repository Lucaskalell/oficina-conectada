package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoriaResumoDTO {
    private Long id;
    private String nome;
    private Long totalItens;
}
