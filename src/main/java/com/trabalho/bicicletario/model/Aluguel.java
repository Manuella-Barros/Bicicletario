package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Aluguel {
    @Id
    private int id;
    private int ciclistaId;
    private int trancaInicio;

    public Aluguel(int id, int ciclistaId, int trancaInicio) {
        this.id = id;
        this.ciclistaId = ciclistaId;
        this.trancaInicio = trancaInicio;
    }

    public Aluguel() {

    }

    public boolean checkIfValid(){
        return this.ciclistaId > 0 && this.trancaInicio > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCiclistaId() {
        return ciclistaId;
    }

    public void setCiclistaId(int ciclistaId) {
        this.ciclistaId = ciclistaId;
    }

    public int getTrancaInicio() {
        return trancaInicio;
    }

    public void setTrancaInicio(int trancaInicio) {
        this.trancaInicio = trancaInicio;
    }
}
