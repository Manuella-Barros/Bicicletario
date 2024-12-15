package com.trabalho.bicicletario.exception;

import com.trabalho.bicicletario.model.ErrorEnum;
import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

    private final String codigo;
    private final String mensagem;
    private final HttpStatus status;

    public CustomException(ErrorEnum errorEnum) {
        super(errorEnum.getMensagem());
        this.codigo = errorEnum.getCodigo();
        this.mensagem = errorEnum.getMensagem();
        this.status = errorEnum.getStatus();
    }

    public CustomException(CustomException customException) {
        super(customException.getMensagem());
        this.codigo = customException.getCodigo();
        this.mensagem = customException.getMensagem();
        this.status = customException.getStatus();
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
