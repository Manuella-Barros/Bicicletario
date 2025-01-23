package com.trabalho.bicicletario.model.integracoes.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TrancaResponse {
    @JsonProperty("id")
    private Integer id;

    @JsonProperty("bicicleta")
    private Integer bicicleta;

    @JsonProperty("numero")
    private Integer numero;

    @JsonProperty("localizacao")
    private String localizacao;

    @JsonProperty("anoDeFabricacao")
    private String anoDeFabricacao;

    @JsonProperty("modelo")
    private String modelo;

    @JsonProperty("status")
    private String status;
}
