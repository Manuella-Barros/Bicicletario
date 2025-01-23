package com.trabalho.bicicletario.model.Enum;

public enum StatusTrancaEnum {
    NOVA("Nova"),
    LIVRE("Livre"),
    OCUPADA("Ocupada"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada"),;

    private String descricao;

    private StatusTrancaEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}