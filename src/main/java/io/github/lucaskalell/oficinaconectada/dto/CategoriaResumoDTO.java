package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResumoDTO {
    private Long id;
    private String nome;
    private Long totalItens;
}
