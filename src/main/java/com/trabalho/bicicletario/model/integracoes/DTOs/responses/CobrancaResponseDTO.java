package com.trabalho.bicicletario.model.integracoes.DTOs.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CobrancaResponseDTO {
    private Integer id;

    private String status;

    private LocalDateTime horaSolicitacao;

    private LocalDateTime horaFinalizacao;

    private double valor;

    private int ciclista;

}
