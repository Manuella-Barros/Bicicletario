package com.trabalho.bicicletario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@RequiredArgsConstructor
@Setter @Getter
public class Funcionario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonIgnore
    private int id;
    private String senha;

    @Column(name = "confirmação_senha")
    private String confirmacaoSenha;

    private String email;
    private String nome;
    private int idade;
    private String funcao;
    private String cpf;
    private int matricula;

    public Funcionario(String senha, String confirmacaoSenha, String email, String nome,
                       int idade, String funcao, String cpf, int matricula) {
        this.matricula = matricula;
        this.nome = nome;
        this.idade = idade;
        this.funcao = funcao;
        this.email = email;
        this.senha = senha;
        this.confirmacaoSenha = confirmacaoSenha;
        this.cpf = cpf;
    }

    public boolean checkIfValid(){
        return this.senha != null && this.confirmacaoSenha != null && this.email != null && this.nome != null
                && this.funcao != null && this.cpf != null && this.matricula != 0;
    }
}
