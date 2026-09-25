package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MecanicoRequestDTO {
    private String nome;
    private String especialidade;
    private String telefone;
    private String email;
    private String senha;
}
