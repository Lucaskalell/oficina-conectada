package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCarroRequestDTO {
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    private String placa;
    private String modelo;
    private String marca;
    private String cor;
    private Integer ano;
}
