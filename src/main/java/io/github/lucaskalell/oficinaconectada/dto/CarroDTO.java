package io.github.lucaskalell.oficinaconectada.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarroDTO {
    private Long id;
    private String placa;
    private String modelo;
    private Integer ano;
    private String cor;

    public CarroDTO() {}

    public CarroDTO(Long id, String placa, String modelo, Integer ano, String cor) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.cor = cor;
    }
}
