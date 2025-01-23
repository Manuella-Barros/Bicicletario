package com.trabalho.bicicletario.model.integracoes.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CobrancaResponseDTO {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("status")
    private String status;

    @JsonProperty("horaSolicitacao")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime horaSolicitacao;

    @JsonProperty("horaFinalizacao")
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime horaFinalizacao;

    @JsonProperty("valor")
    private double valor;

    @JsonProperty("ciclista")
    private int ciclista;
}
