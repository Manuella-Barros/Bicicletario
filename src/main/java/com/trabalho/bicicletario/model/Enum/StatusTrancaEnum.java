package com.trabalho.bicicletario.model.Enum;

public enum StatusTrancaEnum {

    // NO SWAGGER TEM AS OPÇÕES DE ENUM PARA A TRANCA
    //'DESTRANCAR', 'TRANCAR'
    TRANCAR("Trancar"),
    DESTRANCAR("Destrancar"),;
//    NOVA("Nova"),
//    LIVRE("Livre"),
//    OCUPADA("Ocupada"),
//    EM_REPARO("Em reparo"),
//    APOSENTADA("Aposentada"),;

    private String descricao;

    private StatusTrancaEnum(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao(){
        return descricao;
    }
}