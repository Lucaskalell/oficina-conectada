package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;
import java.util.List;

@Data
public class VendaRequestDTO {
    private List<ItemVendaRequestDTO> itens;
}
