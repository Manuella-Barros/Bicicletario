package com.trabalho.bicicletario.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter @Getter
public class Error {
    private String codigo;
    private String mensagem;

    public Error(String mensagem, String codigo) {
        this.mensagem = mensagem;
        this.codigo = codigo;
    }
}
