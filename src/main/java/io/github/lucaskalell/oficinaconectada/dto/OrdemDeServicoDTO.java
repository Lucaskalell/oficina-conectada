package io.github.lucaskalell.oficinaconectada.dto;

import io.github.lucaskalell.oficinaconectada.status.PrioridadeOrdem;
import io.github.lucaskalell.oficinaconectada.status.StatusOrdemDeServico;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrdemDeServicoDTO {
    private Long id;
    private StatusOrdemDeServico status;
    private String placa;
    private String cliente;
    private String carro;
    private LocalDateTime entrada;
    private String defeito;
    private String descricaoServico;
    private BigDecimal valorTotal;
    private List<ItemServicoDTO> itens;
    private List<FotoOrdemDeServicoDTO> fotos;
    private String mecanico;
    private String prioridade;

    public OrdemDeServicoDTO(
            Long id,
            StatusOrdemDeServico status,
            String placa,
            String cliente,
            String carro,
            LocalDateTime dataEntrada,
            String defeito,
            String descricaoServico,
            BigDecimal valorTotal,
            List<ItemServicoDTO> itens,
            List<FotoOrdemDeServicoDTO> fotos,
            String mecanico,
            PrioridadeOrdem prioridade
    ) {
        this.id = id;
        this.status = status;
        this.placa = placa;
        this.cliente = cliente;
        this.carro = carro;
        this.entrada = dataEntrada;
        this.defeito = defeito;
        this.descricaoServico = descricaoServico;
        this.valorTotal = valorTotal;
        this.itens = itens;
        this.fotos = fotos;
        this.mecanico = mecanico;
        this.prioridade = prioridade != null ? prioridade.getDescricao() : null;
    }
}
