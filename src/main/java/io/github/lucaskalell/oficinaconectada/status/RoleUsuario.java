package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum RoleUsuario {
    ADMIN("Administrador"),
    ATENDENTE("Atendente"),
    MECANICO("Mecânico");

    private final String descricao;

    RoleUsuario(String descricao) {
        this.descricao = descricao;
    }
}
