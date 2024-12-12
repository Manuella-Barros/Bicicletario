package com.trabalho.bicicletario.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "ciclista")
public class Ciclista {
    @Id
    private int id;

    @Getter @Setter
    private String nome;

    @Getter @Setter @Column(name = "data_nascimento")
    private Date nascimento;

    @Getter @Setter
    private String cpf;

    @Getter @Setter
    @OneToOne
    private Passaporte passaporte;

    @Getter @Setter
    private String nacionalidade;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String urlFotoDocumento;

    @Getter @Setter
    private String senha;

    @Getter @Setter @Nullable
    private String status;
}
