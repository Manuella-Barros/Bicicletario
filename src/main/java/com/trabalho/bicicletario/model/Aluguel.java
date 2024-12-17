package com.trabalho.bicicletario.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "aluguel")
@RequiredArgsConstructor
@Setter @Getter
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

    public Aluguel(int ciclista, int trancaInicio, int bicicleta) {
        this.ciclista = ciclista;
        this.trancaInicio = trancaInicio;
        this.bicicleta = bicicleta;
    }

    public boolean checkIfValid(){
        return this.trancaInicio > 0;
    }

}
