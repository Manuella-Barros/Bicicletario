package com.trabalho.bicicletario.model.integracoes.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailDTO {

    private String email;

    private String assunto;

    private String mensagem;

}
