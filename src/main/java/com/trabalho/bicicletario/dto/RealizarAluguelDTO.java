package com.trabalho.bicicletario.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter @Getter
public class RealizarAluguelDTO {
    private int ciclista;
    private int trancaInicio;

    public RealizarAluguelDTO(int ciclista, int trancaInicio) {
        this.ciclista = ciclista;
        this.trancaInicio = trancaInicio;
    }
}
