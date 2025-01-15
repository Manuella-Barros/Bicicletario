package com.trabalho.bicicletario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Funcionario")
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

    public Funcionario(Funcionario funcionario) {
        this.matricula = funcionario.getMatricula();
        this.nome = funcionario.getNome();
        this.idade = funcionario.getIdade();
        this.funcao = funcionario.getFuncao();
        this.email = funcionario.getEmail();
        this.senha = funcionario.getSenha();
        this.confirmacaoSenha = funcionario.confirmacaoSenha;
        this.cpf = funcionario.getCpf();
    }

    public boolean checkIfValid(){
        return this.senha != null && this.confirmacaoSenha != null && this.email != null && this.nome != null
                && this.funcao != null && this.cpf != null && this.matricula != 0;
    }
}
