package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CarroDTO {
    private Long id;
    private String placa;
    private String modelo;
    private Integer ano;
    private String cor;
    private Long clienteId;
    private String nomeCliente;

    public CarroDTO(Long id, String placa, String modelo, Integer ano, String cor, String nomeCliente) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.nomeCliente = nomeCliente;
    }

    public CarroDTO(Long id, String placa, String modelo, Integer ano, String cor, Long clienteId, String nomeCliente) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
        this.clienteId = clienteId;
        this.nomeCliente = nomeCliente;
    }
}
