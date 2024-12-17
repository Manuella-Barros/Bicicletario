package com.trabalho.bicicletario.model.integracoes;

public enum StatusBicicletaEnum {
    DISPONIVEL("Disponível"),
    EM_USO("Em uso"),
    REPARO_SOLICITADO("Reparo solicitado"),
    EM_REPARO("Em reparo"),
    APOSENTADA("Aposentada"),
    NOVA("Nova");

    private String descricao;

    private StatusBicicletaEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}
