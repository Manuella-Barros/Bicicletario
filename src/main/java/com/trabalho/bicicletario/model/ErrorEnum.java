package com.trabalho.bicicletario.model;

import org.springframework.http.HttpStatus;

public enum ErrorEnum {
    CICLISTA_CRIADO("Ciclista cadastrado", "201", HttpStatus.CREATED),
    REQUISICAO_MAL_FORMADA("Requisição mal formada", "404", HttpStatus.NOT_FOUND),
    DADOS_INVALIDOS("Dados Inválidos", "422", HttpStatus.UNPROCESSABLE_ENTITY),;

    private String mensagem;
    private String codigo;
    private HttpStatus status;

    ErrorEnum(String mensagem, String codigo, HttpStatus status) {
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
