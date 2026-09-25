package io.github.lucaskalell.oficinaconectada.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MecanicoResponseDTO {
    private Long id;
    private String nome;
    private String especialidade;
    private String telefone;
    private boolean ativo;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
