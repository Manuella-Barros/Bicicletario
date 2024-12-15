package com.trabalho.bicicletario.dto;

public class RealizarAluguelDTO {
    private int ciclista;
    private int trancaInicio;

    public RealizarAluguelDTO(int ciclista, int trancaInicio) {
        this.ciclista = ciclista;
        this.trancaInicio = trancaInicio;
    }

    public int getCiclista() {
        return ciclista;
    }

    public void setCiclista(int ciclistaId) {
        this.ciclista = ciclistaId;
    }

    public int getTrancaInicio() {
        return trancaInicio;
    }

    public void setTrancaInicio(int trancaInicio) {
        this.trancaInicio = trancaInicio;
    }
}
