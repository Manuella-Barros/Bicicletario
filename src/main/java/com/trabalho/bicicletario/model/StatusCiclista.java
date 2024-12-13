package com.trabalho.bicicletario.model;

public enum StatusCiclista {
    ATIVO("ATIVO"),
    INATIVO("INATIVO"),
    AGUARDANDO_CONFIRMACAO("AGUARDANDO_CONFIRMACAO");

    private String descricao;

    StatusCiclista(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
