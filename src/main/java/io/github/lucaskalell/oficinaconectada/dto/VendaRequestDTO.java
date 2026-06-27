package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendaRequestDTO {
    private Long clienteId;
    private Long usuarioId;
    private List<ItemVendaRequestDTO> itens;
}