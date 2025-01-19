package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Setter @Getter
public class Passaporte {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String numero;
    private LocalDate validade;
    private String pais;

    public Passaporte(Passaporte passaporte) {
        if (passaporte == null) {
            return;
        }

        this.numero = passaporte.getNumero();
        this.validade = passaporte.getValidade();
        this.pais = passaporte.getPais();
    }

    public boolean checkIfValid(){
        return this.numero != null && this.validade != null && this.pais != null && this.pais.length() == 2;
    }
}
