package io.github.lucaskalell.oficinaconectada.exception;

public class ArmazenamentoArquivoException extends RuntimeException {
    public ArmazenamentoArquivoException(String mensagem) {
        super(mensagem);
    }

    public ArmazenamentoArquivoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}