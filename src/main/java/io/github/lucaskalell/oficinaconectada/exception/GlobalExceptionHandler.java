package io.github.lucaskalell.oficinaconectada.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleClienteNaoEncontradoException(ClienteNaoEncontradoException clientene) {
        ErrorResponse error = new ErrorResponse(
                clientene.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }


    @ExceptionHandler(CarroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleCarroNaoEncontradoException(CarroNaoEncontradoException cnee) {
        ErrorResponse error = new ErrorResponse(
                cnee.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                System.currentTimeMillis()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
