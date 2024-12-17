package com.trabalho.bicicletario.model;

import com.trabalho.bicicletario.dto.CiclistaDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ciclista")
@RequiredArgsConstructor
@Setter @Getter
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

    public Ciclista(CiclistaResponseDTO ciclistaResponseDTO) {
        this.id = ciclistaResponseDTO.getId();
        this.nascimento = ciclistaResponseDTO.getNascimento();
        this.nome = ciclistaResponseDTO.getNome();
        this.cpf = ciclistaResponseDTO.getCpf();
        this.nacionalidade = ciclistaResponseDTO.getNacionalidade();
        this.email = ciclistaResponseDTO.getEmail();
        this.urlFotoDocumento = ciclistaResponseDTO.getUrlFotoDocumento();
        this.status = ciclistaResponseDTO.getStatus();
    }

    public boolean checkIfValid(){
        return nascimento != null && nome != null && nacionalidade != null && email != null && urlFotoDocumento != null && senha != null;
    }

    public boolean checkIfBrasileiro(){
        return nacionalidade.equalsIgnoreCase("brasileira") || nacionalidade.equalsIgnoreCase("brasileiro") || nacionalidade.equalsIgnoreCase("br");
    }
}
