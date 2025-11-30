package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;

@Data
public class ItemVendaRequestDTO {
    private Long produtoId;
    private Integer quantidade;
}
