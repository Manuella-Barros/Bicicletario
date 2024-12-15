package com.trabalho.bicicletario.model;

public enum StatusCiclistaEnum {
    ATIVO("ATIVO"),
    INATIVO("INATIVO"),
    AGUARDANDO_CONFIRMACAO("AGUARDANDO_CONFIRMACAO");

    private String descricao;

    StatusCiclistaEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
