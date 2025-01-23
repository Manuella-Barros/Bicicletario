package com.trabalho.bicicletario.model.integracoes.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class BicicletaResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("marca")
    private String marca;

    @JsonProperty("modelo")
    private String modelo;

    @JsonProperty("ano")
    private String ano;

    @JsonProperty("numero")
    private Integer numero;

    @JsonProperty("status")
    private String status;
}

