package io.github.lucaskalell.oficinaconectada.status;

import lombok.Getter;

@Getter
public enum PrioridadeOrdem {
    BAIXA("Baixa"),
    MEDIA("Media"),
    ALTA("Alta"),
    URGENTE("Urgente");

    private final String PrioridadeOrdem;
    PrioridadeOrdem(String PrioridadeOrdem) {
        this.PrioridadeOrdem = PrioridadeOrdem;
    }
}
