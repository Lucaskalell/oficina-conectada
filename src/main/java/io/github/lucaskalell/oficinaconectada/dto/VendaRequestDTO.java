package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaRequestDTO {
    private List<ItemVendaRequestDTO> itens;
}