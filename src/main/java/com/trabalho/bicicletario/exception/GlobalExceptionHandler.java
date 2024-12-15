package com.trabalho.bicicletario.exception;

import com.trabalho.bicicletario.model.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // cria um manipulador global de exceções que captura as exceções e retorna a resposta personalizada
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleCustomException(CustomException ex) {
        Error erro = new Error(ex.getCodigo(), ex.getMensagem());

        return new ResponseEntity<>(erro, ex.getStatus());
    }
}
