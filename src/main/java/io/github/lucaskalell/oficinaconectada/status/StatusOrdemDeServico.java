package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum StatusOrdemDeServico {
    EM_ANDAMENTO("EM_ANDAMENTO"),
    NAO_INICIADO("NAO_INICIADO"),
    AGUARDANDO_PECA("AGUARDANDO_PECA"),
    AGUARDANDO_RETIRADA("AGUARDANDO_RETIRADA"),
    FINALIZADO("FINALIZADO"),
    CANCELADO("CANCELADO");

    private final String descricao;

    StatusOrdemDeServico(String descricao) {
        this.descricao = descricao;
    }

}
