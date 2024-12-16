package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Passaporte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;
    private LocalDate validade;
    private String pais;

    public Passaporte(int id, String numero, LocalDate validade, String pais) {
        this.id = id;
        this.numero = numero;
        this.validade = validade;
        this.pais = pais;
    }

    public Passaporte() {

    }

    public boolean checkIfValid(){
        return this.numero != null && this.validade != null && this.pais != null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getValidade() {
        return validade;
    }

    public void setValidade(LocalDate validade) {
        this.validade = validade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
