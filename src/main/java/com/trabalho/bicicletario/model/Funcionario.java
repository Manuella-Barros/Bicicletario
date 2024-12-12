package com.trabalho.bicicletario.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Funcionario {
    @Id
    private int id;

    @Getter @Setter
    private String senha;

    @Getter @Setter
    private String confirmação_senha;

    @Getter @Setter
    private String email;

    @Getter @Setter
    private String nome;

    @Getter @Setter
    private int idade;

    @Getter @Setter
    private String funcao;

    @Getter @Setter
    private String cpf;

    @Getter @Setter
    private int matricula;
}
