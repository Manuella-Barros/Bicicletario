package com.trabalho.bicicletario.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Setter @Getter
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

    public boolean checkIfValid(){
        return this.nomeTitular != null && this.numero != null && this.validade != null && this.cvv != null;
    }
}
