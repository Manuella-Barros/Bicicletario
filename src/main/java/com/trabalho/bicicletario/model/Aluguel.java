package com.trabalho.bicicletario.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aluguel")
public class Aluguel {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_ciclista")
    private int ciclista;

    @Column(name = "id_bicicleta")
    private int bicicleta;

    @Column(name = "id_tranca_inicio")
    private int trancaInicio;

    @Column(name = "id_tranca_fim")
    private int trancaFim;

    @Column(name = "hora_inicio")
    private LocalDateTime horaInicio;

    @Column(name = "hora_fim")
    private LocalDateTime horaFim;
    private double cobranca;

    public Aluguel(int ciclista, int trancaInicio) {
        this.ciclista = ciclista;
        this.trancaInicio = trancaInicio;

    }

    public Aluguel() {

    }

    public boolean checkIfValid(){
        return this.ciclista > 0 && this.trancaInicio > 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTrancaFim() {
        return trancaFim;
    }

    public void setTrancaFim(int trancaFim) {
        this.trancaFim = trancaFim;
    }

    public int getBicicleta() {
        return bicicleta;
    }

    public void setBicicleta(int bicicleta) {
        this.bicicleta = bicicleta;
    }

    public LocalDateTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalDateTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDateTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalDateTime horaFim) {
        this.horaFim = horaFim;
    }

    public double getCobranca() {
        return cobranca;
    }

    public void setCobranca(double cobranca) {
        this.cobranca = cobranca;
    }
}
