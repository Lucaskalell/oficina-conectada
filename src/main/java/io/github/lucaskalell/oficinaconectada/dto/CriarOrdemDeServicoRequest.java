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
    private String defeito;
    private String descricaoServico;
    private BigDecimal valorTotal;
    private BigDecimal valorSubtotalPecas;
    private List<ItemServicoDTO> itens;
    private List<FotoOrdemDeServicoDTO> fotos;

    public CriarOrdemDeServicoRequest(
            Long clienteId,
            Long carroId,
            String defeito,
            String descricaoServico,
            BigDecimal valorTotal,
            BigDecimal valorSubtotalPecas,
            List<ItemServicoDTO> itens,
            List<FotoOrdemDeServicoDTO> fotos
    ) {
        this.clienteId = clienteId;
        this.carroId = carroId;
        this.defeito = defeito;
        this.descricaoServico = descricaoServico;
        this.valorTotal = valorTotal;
        this.valorSubtotalPecas = valorSubtotalPecas;
        this.itens = itens;
        this.fotos = fotos;
    }
}