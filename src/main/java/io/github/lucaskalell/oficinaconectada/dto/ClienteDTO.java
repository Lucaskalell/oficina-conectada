package io.github.lucaskalell.oficinaconectada.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private List<CarroDTO> carros;

    public ClienteDTO(
            Long id,
            String nome,
            String cpf,
            String telefone,
            String email
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public ClienteDTO(
            Long id,
            String nome,
            String cpf,
            String telefone,
            String email,
            List<CarroDTO> carros
    ) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
        this.carros = carros;
    }
}
