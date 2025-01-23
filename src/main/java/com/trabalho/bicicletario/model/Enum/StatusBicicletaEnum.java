package com.trabalho.bicicletario.model.Enum;

public enum StatusBicicletaEnum {
    DISPONIVEL("DISPONÍVEL"),
    EM_USO("EM_USO"),
    NOVA("NOVA"),
    APOSENTADA("APOSENTADA"),
    REPARO_SOLICITADO("REPARO_SOLICITADO"),
    EM_REPARO("EM_REPARO");

    private String descricao;

    StatusBicicletaEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
