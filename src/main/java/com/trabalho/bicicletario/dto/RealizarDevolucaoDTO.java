package com.trabalho.bicicletario.dto;

public class RealizarDevolucaoDTO {
    private int idBicicleta;
    private int idTranca;

    public RealizarDevolucaoDTO(int ciclista, int trancaInicio) {
        this.idBicicleta = ciclista;
        this.idTranca = trancaInicio;
    }

    public int getIdBicicleta() {
        return idBicicleta;
    }

    public void setIdBicicleta(int ciclistaId) {
        this.idBicicleta = ciclistaId;
    }

    public int getIdTranca() {
        return idTranca;
    }

    public void setIdTranca(int idTranca) {
        this.idTranca = idTranca;
    }

}
