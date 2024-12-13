package com.trabalho.bicicletario.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_titular")
    private String nomeTitular;
    private String numero;
    private LocalDate validade;
    private String cvv;

    public Cartao(int id, String nomeTitular, String numero, LocalDate validade, String cvv) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
    }

    public Cartao() {

    }

    public boolean checkIfValid(){
        return this.nomeTitular != null && this.numero != null && this.validade != null && this.cvv != null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
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

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
