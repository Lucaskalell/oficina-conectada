package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum StatusAgendamento {
    AGENDADO("Agendado"),
    CONCLUIDO("Concluído"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusAgendamento(String descricao) {
        this.descricao = descricao;
    }
}