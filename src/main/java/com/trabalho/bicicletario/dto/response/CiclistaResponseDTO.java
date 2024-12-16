package com.trabalho.bicicletario.dto.response;

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

    public CiclistaResponseDTO(Ciclista ciclista) {
        this.id = ciclista.getId();
        this.nascimento = ciclista.getNascimento();
        this.urlFotoDocumento = ciclista.getUrlFotoDocumento();
        this.nome = ciclista.getNome();
        this.cpf = ciclista.getCpf();
        this.nacionalidade = ciclista.getNacionalidade();
        this.email = ciclista.getEmail();
        this.status = ciclista.getStatus();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }
}
