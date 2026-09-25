package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MecanicoUpdateDTO {
    private String nome;
    private String especialidade;
    private String telefone;
    private boolean ativo;
}
