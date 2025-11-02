package io.github.lucaskalell.oficinaconectada.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Builder
@Data
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
}
