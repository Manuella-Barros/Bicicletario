package com.trabalho.bicicletario.dto.recuperarDados;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trabalho.bicicletario.model.Cartao;

import java.time.LocalDate;

public class RecuperarDadosCiclistaDTO {
    protected int identificador;
    protected String nome;
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate nascimento;
    protected String cpf;
    protected String nacionalidade;
    protected String email;
    protected String senha;

    @JsonProperty("statusConfirmacaoConta")
    protected String status;

    Cartao meioDePagamento;

    public RecuperarDadosCiclistaDTO() {}

    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int id) {
        this.identificador = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cartao getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(Cartao meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }
}
