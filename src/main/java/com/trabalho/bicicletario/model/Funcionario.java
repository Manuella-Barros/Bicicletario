package com.trabalho.bicicletario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
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

    public Funcionario() {}

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

    public int getId() {
        return id;
}

    public void setId(int id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
