package io.github.lucaskalell.oficinaconectada.dto;

import io.github.lucaskalell.oficinaconectada.status.RoleUsuario;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String nome;
    private String email;
    private String senha;
    private RoleUsuario role;
}