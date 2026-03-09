package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum StatusAgendamento {
    AGENDADO("AGENDADO"),
    CONCLUIDO("CONCLUIDO"),
    CANCELADO("CANCELADO");


    private final String descricaoAgendamento;
    StatusAgendamento(String descricaoAgendamento) {
        this.descricaoAgendamento = descricaoAgendamento;
    }

}
