package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum StatusOrdemDeServico {
    EM_ANDAMENTO("Em Andamento"),
    NAO_INICIADO("Não Iniciado"),
    AGUARDANDO_PECA("Aguardando Peça"),
    AGUARDANDO_RETIRADA("Aguardando Retirada"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusOrdemDeServico(String descricao) {
        this.descricao = descricao;
    }
}