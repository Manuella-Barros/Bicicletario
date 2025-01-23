package com.trabalho.bicicletario.model.integracoes.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmailResponse {

    @JsonProperty("id")
    private int id;

    @JsonProperty("email")
    private String destinatario;

    @JsonProperty("assunto")
    private String assunto;

    @JsonProperty("mensagem")
    private String mensagem;
}
