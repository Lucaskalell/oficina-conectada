package io.github.lucaskalell.oficinaconectada.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarClienteNaoEncontrado(ClienteNaoEncontradoException excecao) {
        ErrorResponse erro = new ErrorResponse(
                excecao.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(CarroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> tratarCarroNaoEncontrado(CarroNaoEncontradoException excecao) {
        ErrorResponse erro = new ErrorResponse(
                excecao.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ArmazenamentoArquivoException.class)
    public ResponseEntity<ErrorResponse> tratarArmazenamentoArquivo(ArmazenamentoArquivoException excecao) {
        ErrorResponse erro = new ErrorResponse(
                excecao.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}