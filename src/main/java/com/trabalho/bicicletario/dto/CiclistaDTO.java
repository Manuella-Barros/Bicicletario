package com.trabalho.bicicletario.dto;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Passaporte;

public class CiclistaDTO extends Ciclista {
    private Passaporte passaporte;

    public CiclistaDTO(Passaporte passaporte) {
        this.passaporte = passaporte;
    }

    public CiclistaDTO(Ciclista ciclista) {
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

    public CiclistaDTO() {}

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }
}
