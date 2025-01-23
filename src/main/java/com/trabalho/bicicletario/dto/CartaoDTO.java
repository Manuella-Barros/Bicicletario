package com.trabalho.bicicletario.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaoDTO {
    private String nomeTitular;
    private String numero;
    private String validade;
    private String cvv;

    public CartaoDTO(String nomeTitular, String numero, String validade, String cvv) {
        this.nomeTitular = nomeTitular;
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
    }
}
