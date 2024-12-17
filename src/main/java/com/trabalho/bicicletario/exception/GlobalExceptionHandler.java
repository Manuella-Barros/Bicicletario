package com.trabalho.bicicletario.exception;

import com.trabalho.bicicletario.model.Error;
import com.trabalho.bicicletario.model.ErrorEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // cria um manipulador global de exceções que captura as exceções e retorna a resposta personalizada
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Error> handleCustomException(CustomException ex) {
        Error erro = new Error(ex.getMensagem(), ex.getCodigo());

        return new ResponseEntity<>(erro, ex.getStatus());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Error erro = new Error(ErrorEnum.REQUISICAO_MAL_FORMADA.getMensagem(), ErrorEnum.REQUISICAO_MAL_FORMADA.getCodigo());

        return new ResponseEntity<>(erro, ErrorEnum.REQUISICAO_MAL_FORMADA.getStatus());
    }
}
