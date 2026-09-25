package io.github.lucaskalell.oficinaconectada.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private String mensagem;
    private int status;
    private long timestamp;

    public ErrorResponse(String mensagem, int status, long timestamp) {
        this.mensagem = mensagem;
        this.status = status;
        this.timestamp = timestamp;
    }
}