package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Aluguel {
    @Id @Getter @Setter
    private int id;

    @Getter @Setter
    private int ciclistaId;

    @Getter @Setter
    private int trancaInicio;
}
