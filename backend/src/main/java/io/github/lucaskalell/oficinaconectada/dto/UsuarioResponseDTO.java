package io.github.lucaskalell.oficinaconectada.dto;

import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private RoleUsuario role;
}