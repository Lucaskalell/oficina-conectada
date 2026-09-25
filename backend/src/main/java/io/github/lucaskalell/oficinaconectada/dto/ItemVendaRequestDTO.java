package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemVendaRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}