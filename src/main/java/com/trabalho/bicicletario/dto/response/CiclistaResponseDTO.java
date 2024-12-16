package com.trabalho.bicicletario.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Passaporte;

import java.time.LocalDate;

public class CiclistaResponseDTO {
    private int id;
    private LocalDate nascimento;
    private String urlFotoDocumento;
    private String nome;
    private String cpf;
    private String nacionalidade;
    private String email;
    private String status;
    private Passaporte passaporte;
    @JsonIgnore
    private String senha;

    public CiclistaResponseDTO(int id, LocalDate nascimento,
                               String urlFotoDocumento, String nome, String cpf,
                               String nacionalidade, String email, String status, Passaporte passaporte) {
        this.id = id;
        this.nascimento = nascimento;
        this.urlFotoDocumento = urlFotoDocumento;
        this.nome = nome;
        this.cpf = cpf;
        this.nacionalidade = nacionalidade;
        this.email = email;
        this.status = status;
        this.passaporte = passaporte;
    }

    public CiclistaResponseDTO(Ciclista ciclista) {
        this.id = ciclista.getId();
        this.nascimento = ciclista.getNascimento();
        this.urlFotoDocumento = ciclista.getUrlFotoDocumento();
        this.nome = ciclista.getNome();
        this.cpf = ciclista.getCpf();
        this.nacionalidade = ciclista.getNacionalidade();
        this.email = ciclista.getEmail();
        this.status = ciclista.getStatus();
        this.senha = ciclista.getSenha();
    }

    public CiclistaResponseDTO() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public String getUrlFotoDocumento() {
        return urlFotoDocumento;
    }

    public void setUrlFotoDocumento(String urlFotoDocumento) {
        this.urlFotoDocumento = urlFotoDocumento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }
}
