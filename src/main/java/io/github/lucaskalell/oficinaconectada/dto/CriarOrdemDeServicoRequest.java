package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CriarOrdemDeServicoRequest {
    private Long clienteId;
    private Long carroId;
    private Long mecanicoId;
    private String defeito;
    private String descricaoServico;
    private BigDecimal valorTotal;
    private List<ItemServicoDTO> itens;
}
