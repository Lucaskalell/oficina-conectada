package io.github.lucaskalell.oficinaconectada.dto;


import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class CriarOrdemDeServicoRequest {
    private Long clienteId;
    private Long carroId;
    private String defeito;
    private String descricaoServico;
    private BigDecimal valorTotal;

    public CriarOrdemDeServicoRequest(
            Long clienteId,
            Long carroId,
            String defeito,
            String descricaoServico,
            BigDecimal valorTotal
    ) {
        this.clienteId = clienteId;
        this.carroId = carroId;
        this.defeito = defeito;
        this.descricaoServico = descricaoServico;
        this.valorTotal = valorTotal;
    }


    public String getDescricaoServico() {
        return descricaoServico;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public Long getCarroId() {
        return carroId;
    }

    public String getDefeito() {
        return defeito;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

}
