package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum PrioridadeOrdem {
    BAIXA("Baixa"),
    MEDIA("Média"),
    ALTA("Alta"),
    URGENTE("Urgente");

    private final String descricao;

    PrioridadeOrdem(String descricao) {
        this.descricao = descricao;
    }
}