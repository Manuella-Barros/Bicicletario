package com.trabalho.bicicletario.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.trabalho.bicicletario.dto.CiclistaDTO;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ciclista")
public class Ciclista {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    @Column(name = "data_nascimento")
    protected LocalDate nascimento;

    @Column(name = "id_passaporte")
    protected int idPassaporte;

    @Column(name = "id_cartao")
    protected int idCartao;

    @Column(name = "url_foto_documento")
    protected String urlFotoDocumento;

    protected String nome;
    protected String cpf;
    protected String nacionalidade;
    protected String email;

    protected String senha;
    protected String status;

    public Ciclista() {

    }

    public Ciclista(CiclistaDTO ciclistaDTO) {
        this.id = ciclistaDTO.getId();
        this.nascimento = ciclistaDTO.getNascimento();
        this.nome = ciclistaDTO.getNome();
        this.cpf = ciclistaDTO.getCpf();
        this.nacionalidade = ciclistaDTO.getNacionalidade();
        this.email = ciclistaDTO.getEmail();
        this.urlFotoDocumento = ciclistaDTO.getUrlFotoDocumento();
        this.senha = ciclistaDTO.getSenha();
        this.status = ciclistaDTO.getStatus();
        this.idPassaporte = ciclistaDTO.getIdPassaporte();
        this.idCartao = ciclistaDTO.getIdCartao();
    }

    public boolean checkIfValid(){
        if(nascimento == null || nome == null || nacionalidade == null || email == null || urlFotoDocumento == null || senha == null || idCartao <= 0){
            return false;
        }

        if(this.checkIfBrasileiro()){
            return cpf != null;
        }

        return idPassaporte > 0;
    }

    public boolean checkIfBrasileiro(){
        return nacionalidade.equalsIgnoreCase("brasileira") || nacionalidade.equalsIgnoreCase("brasileiro") || nacionalidade.equalsIgnoreCase("br");
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUrlFotoDocumento() {
        return urlFotoDocumento;
    }

    public void setUrlFotoDocumento(String urlFotoDocumento) {
        this.urlFotoDocumento = urlFotoDocumento;
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

    public LocalDate getNascimento() {
        return nascimento;
    }

    public void setNascimento(LocalDate nascimento) {
        this.nascimento = nascimento;
    }

    public int getIdPassaporte() {
        return idPassaporte;
    }

    public void setIdPassaporte(int idPassaporte) {
        this.idPassaporte = idPassaporte;
    }

    public int getIdCartao() {
        return idCartao;
    }

    public void setIdCartao(int idCartao) {
        this.idCartao = idCartao;
    }
}
