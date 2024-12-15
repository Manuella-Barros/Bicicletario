package com.trabalho.bicicletario.model;

public class Error {
    private String codigo;
    private String mensagem;

    public Error(String mensagem, String codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
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
}
